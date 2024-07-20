package com.example.payroll.controller;

import com.example.payroll.model.PaySlip;
import com.example.payroll.model.resignationmodel;
import com.example.payroll.service.ManagerService;
import com.example.payroll.service.ResignationService;
import com.example.payroll.service.SalaryUpdateService;
import com.example.payroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    private ResignationService resignationService;
    @Autowired
    private SalaryUpdateService salaryUpdateService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;


    @PostMapping("/requestSalaryUpdate/{userId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> requestSalaryUpdate(@PathVariable String userId, @RequestParam Double proposedSalary) {
        salaryUpdateService.createUpdateRequest(userId, proposedSalary);
        return ResponseEntity.ok("Salary update request submitted.");
    }

    @GetMapping("/payslips")
      @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<PaySlip>> getPayslipsForOwnDepartment() {
        List<PaySlip> payslips = managerService.getPaySlipsForOwnDepartment();
        return ResponseEntity.ok(payslips);
    }

    @GetMapping("/payslip")
    public ResponseEntity<List<PaySlip>> viewOwnPayslips(@PathVariable String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserId = authentication.getName();

        if (!userId.equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<PaySlip> payslips = userService.viewOwnPaySlips();
        return ResponseEntity.ok(payslips);
    }

    @GetMapping("/resignations")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<resignationmodel>> getResignationRequests() {
        List<resignationmodel> requests = resignationService.getResignationRequestsForManager();
        return ResponseEntity.ok(requests);
    }


    @PostMapping("/resignations/process/{requestId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<resignationmodel> processResignationRequest(
            @PathVariable String requestId,
            @RequestParam boolean accept) {
        resignationmodel request = resignationService.processResignationRequest(requestId, accept);
        return ResponseEntity.ok(request);
    }
}


