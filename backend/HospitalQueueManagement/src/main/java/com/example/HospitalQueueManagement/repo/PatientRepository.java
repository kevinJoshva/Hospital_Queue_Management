package com.example.HospitalQueueManagement.repo;

import com.example.HospitalQueueManagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
