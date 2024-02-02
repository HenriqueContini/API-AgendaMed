package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.SpecializationDTO;
import com.henrique.APIAgendaMed.services.SpecializationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/specialization")
@Tag(name = "Specialization", description = "Endpoints for specialization management")
public class SpecializationController {
    @Autowired
    private SpecializationService service;

    @GetMapping
    public ResponseEntity<List<SpecializationDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
