package com.henrique.APIAgendaMed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henrique.APIAgendaMed.models.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

public record AppointmentDTO(String id, @NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime date, @NotNull String doctorId,
                             @NotNull String patientId, Status status) {
}
