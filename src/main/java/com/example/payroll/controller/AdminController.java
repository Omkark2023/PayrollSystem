package com.example.payroll.controller;

import com.example.payroll.model.PaySlip;
import com.example.payroll.model.SalaryUpdate;
import com.example.payroll.model.resignationmodel;
import com.example.payroll.service.PaySlipService;
import com.example.payroll.service.SalaryUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private SalaryUpdateService salaryUpdateService;

    @Autowired
    private PaySlipService paySlipService;

    @PostMapping("/updateSalary/process/{requestId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SalaryUpdate> processappraisalrequest(
            @PathVariable String requestId,
            @RequestParam boolean accept) {
        SalaryUpdate request = salaryUpdateService.processSalaryRequest(requestId, accept);
        return ResponseEntity.ok(request);
    }
    @PostMapping("/postpayslip/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PaySlip> addPaySlip(@PathVariable String email , @RequestParam Double salary) {
        PaySlip savedPaySlip = paySlipService.savePaySlip(email,salary);
        return ResponseEntity.ok(savedPaySlip);
    }

    @GetMapping("/getsalaryAppraisal")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SalaryUpdate>> getSalaryRequest() {
        List<SalaryUpdate> salaryUpdateList = salaryUpdateService.getSalaryRequest();
        return ResponseEntity.ok(salaryUpdateList);
    }

}
