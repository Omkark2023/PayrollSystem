package com.example.payroll.controller;

import com.example.payroll.model.PaySlip;
import com.example.payroll.model.resignationmodel;
import com.example.payroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/resign")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> submitResignation() {
        resignationmodel resignationRequest = userService.submitResignationRequest();
        return ResponseEntity.ok(resignationRequest);
    }

    @GetMapping("/payslip")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PaySlip>> viewOwnPayslips() {
        List<PaySlip> payslips = userService.viewOwnPaySlips();
        return ResponseEntity.ok(payslips);
    }
}