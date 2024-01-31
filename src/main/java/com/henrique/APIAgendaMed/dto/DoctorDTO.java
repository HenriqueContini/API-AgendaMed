package com.henrique.APIAgendaMed.dto;

import com.henrique.APIAgendaMed.models.Specialization;

import java.time.LocalTime;

public record DoctorDTO(Long id, String name, Specialization specialization, LocalTime startTime, LocalTime finishTime) {
}
