package com.example.payroll.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaySlip {
    @Id
    private String id;
    private String empemail;
    private String emp_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date payDate;
    private Double amount;
}
