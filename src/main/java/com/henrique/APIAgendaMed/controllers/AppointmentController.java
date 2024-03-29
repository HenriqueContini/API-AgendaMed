package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.AppointmentDTO;
import com.henrique.APIAgendaMed.dto.AvailabilityDTO;
import com.henrique.APIAgendaMed.dto.DateDTO;
import com.henrique.APIAgendaMed.services.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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
    public ResponseEntity<AppointmentDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<AppointmentDTO>> findByDoctorId(@PathVariable String id) {
        return ResponseEntity.ok(service.findByDoctorId(id));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<AppointmentDTO>> findByPatientId(@PathVariable String id) {
        return ResponseEntity.ok(service.findByPatientId(id));
    }

    @GetMapping("/doctor/{id}/availability")
    public ResponseEntity<AvailabilityDTO> getAvailability(@PathVariable String id, @RequestParam(value = "date") LocalDate date) {
        return ResponseEntity.ok(service.getAvailability(id, date));
    }

    @PostMapping()
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody AppointmentDTO dto) {
        AppointmentDTO appointment = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(appointment.id()).toUri();
        return ResponseEntity.created(uri).body(appointment);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateDate(@PathVariable String id, @Valid @RequestBody DateDTO dateDTO) {
        return ResponseEntity.ok(service.updateDate(id, dateDTO.date()));
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<AppointmentDTO> cancel(@PathVariable String id) {
        return ResponseEntity.ok(service.cancel(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTO> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
