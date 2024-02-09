package com.henrique.APIAgendaMed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henrique.APIAgendaMed.models.enums.Status;

import java.time.LocalDateTime;
import java.util.Date;

public record AppointmentDTO(String id, @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime date, String doctorId,
                             String patientId, Status status) {
}
