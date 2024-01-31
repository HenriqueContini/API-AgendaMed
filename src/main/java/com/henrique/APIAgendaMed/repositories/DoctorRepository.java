package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
