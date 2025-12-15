package com.example.HospitalQueueManagement.service;

import com.example.HospitalQueueManagement.dto.AdminSignupRequest;
import com.example.HospitalQueueManagement.dto.DoctorSignupRequest;
import com.example.HospitalQueueManagement.dto.PatientSignupRequest;
import com.example.HospitalQueueManagement.dto.ReceptionistSignupRequest;
import com.example.HospitalQueueManagement.model.*;
import com.example.HospitalQueueManagement.repo.*;
import com.example.HospitalQueueManagement.secutity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AuthServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MyUserDetailsService service;

    @Autowired
    private JwtUtil jwtUtil;




        @Autowired private PatientRepository patientRepo;
        @Autowired private DoctorRepository doctorRepo;
        @Autowired private ReceptionistRepository receptionistRepo;
        @Autowired private AdminRepository adminRepo;

    // ---------------- DOCTOR SIGNUP (ADMIN ONLY) ----------------

        public void doctorSignup(DoctorSignupRequest dto) {

            Users user = createUser(
                    dto.getFullName(),
                    dto.getEmail(),
                    dto.getPassword(),
                    "DOCTOR",
                    dto.getDateOfBirth()
            );

            Doctor doctor = new Doctor();
            doctor.setUsers(user);
            doctor.setFullName(dto.getFullName());
            doctor.setMobile(dto.getMobile());
            doctor.setSpecialization(dto.getSpecialization());
            doctor.setDepartment(dto.getDepartment());
            doctor.setRegistrationNumber(dto.getRegistrationNumber());
            doctor.setExperience(dto.getExperience());
            doctor.setConsultationTime(dto.getConsultationTime());
            doctor.setAvailability(dto.getAvailability());

            doctorRepo.save(doctor);
        }


    // ---------------- PATIENT SIGNUP (PUBLIC) ----------------

    public void patientSignup(PatientSignupRequest dto) {

        Users user = createUser(
                dto.getFullName(),
                dto.getEmail(),
                dto.getPassword(),
                "PATIENT",
                dto.getDateOfBirth()
        );

        Patient patient = new Patient();
        patient.setUsers(user);
        patient.setFullName(dto.getFullName());
        patient.setMobile(dto.getMobile());
        patient.setAge(calculateAge(dto.getDateOfBirth())); // optional
        patient.setGender(dto.getGender());
        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setAgreeTerms(dto.getAgreeTerms());

        patientRepo.save(patient);
    }

    private Integer calculateAge(LocalDate dob) {
        if (dob == null) return null;
        return Period.between(dob, LocalDate.now()).getYears();
    }



    // ---------------- RECEPTIONIST SIGNUP (ADMIN ONLY) ----------------
        public void receptionistSignup(ReceptionistSignupRequest dto) {

            Users user = createUser(
                    dto.getFullName(),
                    dto.getEmail(),
                    dto.getPassword(),
                    "RECEPTIONIST"
                    ,null

            );

            Receptionist r = new Receptionist();
            r.setUser(user);
            r.setFullName(dto.getFullName());
            r.setMobile(dto.getMobile());
            r.setShift(dto.getShift());

            receptionistRepo.save(r);
        }


    // ---------------- ADMIN SIGNUP (ONE TIME) ----------------
        public void adminSignup(AdminSignupRequest dto) {

            if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Admin already exists");
            }

            Users user = createUser(
                    dto.getAdminName(),
                    dto.getEmail(),
                    dto.getPassword(),
                    "ADMIN",
                   null
            );

            Admin admin = new Admin();
            admin.setUser(user);
            admin.setHospitalName(dto.getHospitalName());
            admin.setAdminName(dto.getAdminName());
            admin.setMobile(dto.getMobile());

            adminRepo.save(admin);
        }


    // ---------------- COMMON METHOD ----------------
    private Users createUser(String fullName,String email,
                             String password,
                             String role,
                             LocalDate dob) {

        if (userRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Users user = new Users();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);


        user.setDateOfBirth(dob);

        return userRepo.save(user);
    }



}




