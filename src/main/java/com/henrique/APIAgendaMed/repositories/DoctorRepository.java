package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecializationNameContaining(String specialization);
}
