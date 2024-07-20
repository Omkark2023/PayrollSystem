package com.example.payroll.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationRequest {

    public String Email;
    public String Password;
}
