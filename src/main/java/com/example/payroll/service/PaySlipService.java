package com.example.payroll.service;

import com.example.payroll.model.PaySlip;
import com.example.payroll.model.User;
import com.example.payroll.repository.PaySlipRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PaySlipService {
    @Autowired
    private PaySlipRepository paySlipRepository;

    @Autowired
    private UserRepo userRepository;

    public PaySlip savePaySlip(String email , Double Salary) {

        Optional<User> user = userRepository.findByEmpemail(email);
        PaySlip paySlip = new PaySlip();
        paySlip.setId(user.get().getEmp_id());
        paySlip.setEmpemail(user.get().getEmpemail());
        paySlip.setEmp_name(user.get().getEmp_name());
        paySlip.setPayDate(new Date());
        paySlip.setAmount(Salary);
        return paySlipRepository.save(paySlip);
    }
}
