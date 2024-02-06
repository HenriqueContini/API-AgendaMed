package com.henrique.APIAgendaMed.dto;

import com.henrique.APIAgendaMed.models.enums.Status;

import java.util.Date;

public record AppointmentDTO(Long id, Date date, Long doctorId, Long patientId, Status status) {
}
