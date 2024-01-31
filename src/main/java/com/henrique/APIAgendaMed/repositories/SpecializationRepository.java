package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
