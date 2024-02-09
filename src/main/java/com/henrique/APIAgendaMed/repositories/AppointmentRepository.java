package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

}
