package com.example.payroll.service;

import com.example.payroll.model.PaySlip;
import com.example.payroll.model.resignationmodel;
import com.example.payroll.model.User;
import com.example.payroll.repository.PaySlipRepository;
import com.example.payroll.repository.ResignationRequestRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import java.util.List;

@Service
public class DatamanagementService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ResignationRequestRepository resignationRequestRepository;

    @Autowired
    private PaySlipRepository paySlipRepository;

    @Autowired
    private BackupService backupService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void processResignedEmployees() {

        Date today = getTodayMidnight();
        List<resignationmodel> resignationRequests = resignationRequestRepository.findByDate31DaysAfterAcceptance(today);
        for (resignationmodel request : resignationRequests) {
            PaySlip user = paySlipRepository.findById(request.getEmpemail()).orElse(null);
            if (user != null) {
                backupService.createBackup(user, "Payroll_BackUP");
                userRepository.deleteById(user.getEmpemail());
                resignationRequestRepository.deleteById(request.getEmpemail());
                paySlipRepository.deleteById(request.getEmpemail());
            }
        }
    }

    private Date getTodayMidnight() {
        LocalDate today = LocalDate.now();
        return Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
