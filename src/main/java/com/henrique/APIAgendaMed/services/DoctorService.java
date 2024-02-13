package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.dto.SpecializationDTO;
import com.henrique.APIAgendaMed.exceptions.NotFoundException;
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

    @Autowired
    private SpecializationService specializationService;

    public List<DoctorDTO> findAll() {
        List<Doctor> list = repository.findAll();
        List<DoctorDTO> listDTO = new ArrayList<>();

        for (Doctor d: list) {
            listDTO.add(new DoctorDTO(d.getId(), d.getName(), d.getSpecialization(), d.getStartTime(), d.getFinishTime()));
        }

        return listDTO;
    }

    public DoctorDTO findById(String id) {
        Doctor doctor = repository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
        return new DoctorDTO(doctor.getId(), doctor.getName(), doctor.getSpecialization(), doctor.getStartTime(), doctor.getFinishTime());
    }

    public List<DoctorDTO> findBySpecialization(String specialization) {
        List<SpecializationDTO> specializationDTOList = specializationService.findByName(specialization);
        List<String> idList = specializationDTOList.stream().map(SpecializationDTO::id).toList();

        List<DoctorDTO> listDTO = new ArrayList<>();

        if (idList.isEmpty()) return listDTO;

        List<Doctor> list = repository.findBySpecializationId(idList.getFirst());

        for (Doctor d: list) {
            listDTO.add(new DoctorDTO(d.getId(), d.getName(), d.getSpecialization(), d.getStartTime(), d.getFinishTime()));
        }

        return listDTO;
    }
}
