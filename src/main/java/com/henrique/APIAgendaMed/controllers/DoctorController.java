package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.services.DoctorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/doctor")
@Tag(name = "Doctor", description = "Endpoints for doctor management")
public class DoctorController {
    @Autowired
    private DoctorService service;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id) {
        System.out.println(id);
        return ResponseEntity.ok(service.findById(id));
    }
}
