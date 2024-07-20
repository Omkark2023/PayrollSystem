package com.example.payroll.service;

import com.example.payroll.model.User;
import com.example.payroll.model.resignationmodel;
import com.example.payroll.repository.ResignationRequestRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResignationService {

    @Autowired
    private ResignationRequestRepository resignationRequestRepository;

    @Autowired
    private UserRepo userRepository;

    public List<resignationmodel> getResignationRequestsForManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
//        User manager = userRepository.findByEmpemail(email)
//                .orElseThrow(() -> new RuntimeException("Manager not found with email: " + email));
        return resignationRequestRepository.findByManagerAndIsAccepted(email, false);
    }
    @Transactional
    public resignationmodel processResignationRequest(String empmail, boolean isAccepted) {

        resignationmodel request = resignationRequestRepository.findByEmpemail(empmail)
                .orElseThrow(() -> new RuntimeException("Resignation request not found"));

        if (isAccepted) {
            acceptResignation(request);
        } else {
            declineResignation(request);
        }
        return request;
    }

    private void acceptResignation(resignationmodel request) {
        if (request.getEmpemail() == null) {
            throw new IllegalStateException("Request must have an email to be updated");
        }
        request.setAccepted(true);
        request.setAcceptedDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getAcceptedDate());
        calendar.add(Calendar.DATE, 31);
        request.setDate31DaysAfterAcceptance(calendar.getTime());
        updateAssociatedUser(request);
        resignationRequestRepository.save(request);
    }

    private void declineResignation(resignationmodel request) {
        resignationRequestRepository.delete(request);
    }


    private void updateAssociatedUser(resignationmodel request) {
        User user = userRepository.findByEmpemail(request.getEmpemail())
                .orElseThrow(() -> new RuntimeException("User not found for resignation"));
        user.setDate31DaysAfterAcceptance(request.getDate31DaysAfterAcceptance());
        userRepository.save(user);
    }
}
