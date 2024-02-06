package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.AppointmentDTO;
import com.henrique.APIAgendaMed.services.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/appointment")
@Tag(name = "Appointment", description = "Endpoints for appointment management")
public class AppointmentController {
    @Autowired
    private AppointmentService service;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO dto) {
        AppointmentDTO appointment = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(appointment.id()).toUri();
        return ResponseEntity.created(uri).body(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
