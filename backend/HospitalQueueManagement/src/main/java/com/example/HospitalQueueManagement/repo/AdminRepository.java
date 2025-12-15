package com.example.HospitalQueueManagement.repo;

import com.example.HospitalQueueManagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
