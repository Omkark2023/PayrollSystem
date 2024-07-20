package com.example.payroll.service;


import com.example.payroll.model.PaySlip;
import com.example.payroll.model.User;
import com.example.payroll.repository.PaySlipRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private UserRepo employeeRepository;

    @Autowired
    private PaySlipRepository paySlipRepository;


    @Scheduled(cron = "0 0 0 1 * ?")
    public void processMonthlySalaries() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        List<User> employees = employeeRepository.findAll();

        for (User employee : employees) {
            double salary = employee.getSalary() / 12;
            if (employee.getDate31DaysAfterAcceptance() != null) {
                Calendar resignCal = Calendar.getInstance();
                resignCal.setTime(employee.getDate31DaysAfterAcceptance());
                int resignMonth = resignCal.get(Calendar.MONTH);
                int resignYear = resignCal.get(Calendar.YEAR);

                if (resignYear == year && resignMonth == month) {
                    int daysWorked = resignCal.get(Calendar.DAY_OF_MONTH);
                    int totalDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    salary = (salary / totalDaysInMonth) * daysWorked;
                }
            }

            PaySlip paySlip = new PaySlip();
            paySlip.setEmpemail(employee.getEmpemail());
            paySlip.setEmp_name(employee.getEmp_name());
            paySlip.setPayDate(today);
            paySlip.setAmount(salary);
            paySlipRepository.save(paySlip);
        }
    }
}
