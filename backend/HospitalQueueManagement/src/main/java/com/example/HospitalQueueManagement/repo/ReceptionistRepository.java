package com.example.HospitalQueueManagement.repo;

import com.example.HospitalQueueManagement.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionistRepository extends JpaRepository<Receptionist,Long> {
}
