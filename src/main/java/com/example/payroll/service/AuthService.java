package com.example.payroll.service;

import com.example.payroll.model.AuthenticationRequest;
import com.example.payroll.controller.AuthenticationResponse;
import com.example.payroll.model.Registerreq;
import com.example.payroll.model.Role;
import com.example.payroll.model.User;
import com.example.payroll.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(Registerreq request) {
        Role userRole;
        try {
            userRole = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + request.getRole());
        }
        User user = User.builder()
                .emp_name(request.getEmp_name())
                .empemail(request.getEmp_email())
                .password(passwordEncoder.encode(request.getPassword()))
                .emp_address(request.getEmp_address())
                .department(request.getDepartment())
                .emp_id(request.getEmp_id())
                .emp_phone(request.getEmp_phone())
                .salary(request.getSalary())
                .manager(request.getManager())
                .role(userRole)
                .build();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email already exists: " + request.getEmp_email());
        }
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User userDetails = userRepository.findByEmpemail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }
}
