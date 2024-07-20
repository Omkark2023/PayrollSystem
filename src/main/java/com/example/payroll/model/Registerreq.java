package com.example.payroll.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Registerreq {
    @Id
    private String emp_id;
    private String emp_name;
    private String emp_email;
    private String emp_phone;
    private String emp_address;
    private String department;
    private String password;
    private Double salary;
    private String manager;
    private String role;
}
