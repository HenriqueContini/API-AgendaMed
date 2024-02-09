package com.henrique.APIAgendaMed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henrique.APIAgendaMed.models.Specialization;

import java.time.LocalTime;

public record DoctorDTO(String id, String name, Specialization specialization, @JsonFormat(pattern = "HH:mm") LocalTime startTime,
                        @JsonFormat(pattern = "HH:mm") LocalTime finishTime) {
}
