package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.models.Doctor;
import com.henrique.APIAgendaMed.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository repository;

    public List<DoctorDTO> findAll() {
        List<Doctor> list = repository.findAll();
        List<DoctorDTO> listDTO = new ArrayList<>();

        for (Doctor d: list) {
            listDTO.add(new DoctorDTO(d.getId(), d.getName(), d.getSpecialization(), d.getStartTime(), d.getFinishTime()));
        }

        return listDTO;
    }
}
