package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Specialization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpecializationRepository extends MongoRepository<Specialization, String> {
    List<Specialization> findByNameIgnoreCaseContaining(String name);
}
