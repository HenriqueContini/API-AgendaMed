package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
