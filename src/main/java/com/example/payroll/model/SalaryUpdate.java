package com.example.payroll.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "salaryUpdateRequests")
public class SalaryUpdate {
    @Id
    private String id;
    private String empname;
    private String empemail;
    private String department;
    private Double proposedSalary;
    private boolean isApproved;
}
