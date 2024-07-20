package com.example.payroll.repository;

import com.example.payroll.model.SalaryUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface SalaryUpdateRequestRepository extends MongoRepository<SalaryUpdate, String> {
    Optional<SalaryUpdate> findByEmpemail(String emp_email);
    List<SalaryUpdate> findByDepartmentAndIsApproved(String department ,Boolean isApproved);

}
