package com.example.payroll.repository;

import com.example.payroll.model.PaySlip;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Date;
import java.util.Optional;

public interface PaySlipRepository extends MongoRepository<PaySlip, String> {
    List<PaySlip> findByEmpemailIn(List<String> userId);
    List<PaySlip> findByEmpemail(String email);
}
