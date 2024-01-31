package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {
    @Autowired
    private DoctorService service;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
