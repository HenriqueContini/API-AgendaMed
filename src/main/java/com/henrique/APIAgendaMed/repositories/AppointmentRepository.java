package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByDoctorId(String id);
    List<Appointment> findByPatientId(String id);
}
