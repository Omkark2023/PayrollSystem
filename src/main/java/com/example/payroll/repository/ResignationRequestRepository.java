package com.example.payroll.repository;

import com.example.payroll.model.resignationmodel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ResignationRequestRepository extends MongoRepository<resignationmodel, String> {
    Optional<resignationmodel> findByEmpemail(String empemail);
    List<resignationmodel> findByDate31DaysAfterAcceptance(Date date31DaysAfterAcceptance);
    List<resignationmodel> findByManagerAndIsAccepted(String manager, boolean isAccepted);
}

