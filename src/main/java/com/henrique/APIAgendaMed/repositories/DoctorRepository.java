package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findBySpecializationId(String specializationId);
}
