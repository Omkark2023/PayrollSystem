package com.example.payroll.service;

import com.example.payroll.model.PaySlip;
import com.example.payroll.model.User;
import com.example.payroll.repository.PaySlipRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PaySlipRepository paySlipRepository;

    public List<PaySlip> getPaySlipsForOwnDepartment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User manager = userRepository.findByEmpemail(email)
                .orElseThrow(() -> new RuntimeException("Manager not found with email: " + email));

        List<User> usersInDepartment = userRepository.findByManager(manager.getEmpemail());

        if (usersInDepartment.isEmpty()) {
            return List.of();
        }

        List<String> userIds = usersInDepartment.stream()
                .map(User::getEmpemail)
                .collect(Collectors.toList());

        return paySlipRepository.findByEmpemailIn(userIds);
    }
}
