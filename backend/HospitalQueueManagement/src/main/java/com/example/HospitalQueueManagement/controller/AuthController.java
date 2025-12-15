package com.example.HospitalQueueManagement.controller;

import com.example.HospitalQueueManagement.dto.*;
import com.example.HospitalQueueManagement.model.Users;
import com.example.HospitalQueueManagement.repo.UserRepo;
import com.example.HospitalQueueManagement.secutity.JwtUtil;
import com.example.HospitalQueueManagement.service.AuthServices;
import com.example.HospitalQueueManagement.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthServices authService;


    @Autowired
    private MyUserDetailsService service;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signup")
    public String signup(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "Signup successful!";
    }

    // ---------------- PATIENT SIGNUP (PUBLIC) ----------------
    @PostMapping("/signup/patient")
    public String patientSignup(@RequestBody PatientSignupRequest req) {
        authService.patientSignup(req);
        return "Patient Registered Successfully";
    }

    // ---------------- ADMIN SIGNUP ----------------
    @PostMapping("/signup/admin")
    public String adminSignup(@RequestBody AdminSignupRequest req) {
        authService.adminSignup(req);
        return "Admin Created Successfully";
    }

    // ---------------- DOCTOR SIGNUP (ADMIN TOKEN REQUIRED) ----------------
    @PostMapping("/signup/doctor")
    public String doctorSignup(@RequestBody DoctorSignupRequest req) {
        authService.doctorSignup(req);
        return "Doctor Account Created";
    }

    // ---------------- RECEPTIONIST SIGNUP (ADMIN TOKEN REQUIRED) ----------------
    @PostMapping("/signup/receptionist")
    public String receptionistSignup(@RequestBody ReceptionistSignupRequest req) {
        authService.receptionistSignup(req);
        return "Receptionist Account Created";
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {



        Users user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");


        }
        if (!user.getRole().equals(request.getRole()))
            throw new RuntimeException("Invalid Role");

        UserDetails details = service.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(details);
        return Map.of(
                "token", token,
                "role", user.getRole()
        );
    }
}
