package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.services.DoctorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/doctor")
@Tag(name = "Doctor", description = "Endpoints for doctor management")
public class DoctorController {
    @Autowired
    private DoctorService service;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll(@RequestParam(value = "specialization", defaultValue = "") String specialization) {
        List<DoctorDTO> list = specialization.isBlank() ? service.findAll() : service.findBySpecialization(specialization);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
