package com.example.payroll.repository;

import com.example.payroll.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findByEmpemail(String emp_email);
    Optional<User> findByEmpemailAndManager(String email , String manager);
    List<User> findByManager(String manager);

}
