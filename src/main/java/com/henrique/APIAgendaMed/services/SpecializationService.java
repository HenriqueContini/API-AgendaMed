package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.SpecializationDTO;
import com.henrique.APIAgendaMed.exceptions.NotFoundException;
import com.henrique.APIAgendaMed.models.Specialization;
import com.henrique.APIAgendaMed.repositories.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecializationService {
    @Autowired
    private SpecializationRepository repository;

    public List<SpecializationDTO> findAll() {
        List<Specialization> list = repository.findAll();
        List<SpecializationDTO> listDTO = new ArrayList<>();

        for (Specialization s : list) {
            listDTO.add(new SpecializationDTO(s.getId(), s.getName()));
        }

        return listDTO;
    }

    public SpecializationDTO findById(String id) {
        Specialization specialization = repository.findById(id).orElseThrow(() -> new NotFoundException("Specialization not found"));
        return new SpecializationDTO(specialization.getId(), specialization.getName());
    }
}
