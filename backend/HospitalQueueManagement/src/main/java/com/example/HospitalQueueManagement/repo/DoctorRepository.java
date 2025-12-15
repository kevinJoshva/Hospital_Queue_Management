package com.example.HospitalQueueManagement.repo;

import com.example.HospitalQueueManagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
