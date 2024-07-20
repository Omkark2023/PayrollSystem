package com.example.payroll.service;

import com.example.payroll.model.resignationmodel;
import com.example.payroll.model.PaySlip;
import com.example.payroll.model.User;
import com.example.payroll.repository.ResignationRequestRepository;
import com.example.payroll.repository.PaySlipRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private ResignationRequestRepository resignationRequestRepository;
    @Autowired
    private PaySlipRepository paySlipRepository;

    public resignationmodel submitResignationRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmpemail(email).orElseThrow(() -> new RuntimeException("User not found"));

        resignationmodel newRequest = new resignationmodel();
        newRequest.setId(user.getEmp_id());
        newRequest.setEmpemail(user.getEmpemail());
        newRequest.setEmpname(user.getEmp_name());
        newRequest.setDepartment(user.getDepartment());
        newRequest.setManager(user.getManager());
        newRequest.setRequestDate(new Date());
        newRequest.setAccepted(false);
        return resignationRequestRepository.save(newRequest);
    }

    public List<PaySlip> viewOwnPaySlips() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmpemail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return paySlipRepository.findByEmpemail(email);
    }
}
