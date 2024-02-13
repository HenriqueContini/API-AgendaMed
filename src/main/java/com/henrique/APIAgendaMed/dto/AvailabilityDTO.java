package com.henrique.APIAgendaMed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AvailabilityDTO(@JsonFormat(pattern = "yyyy-MM-dd") LocalDate date, @JsonFormat(pattern = "HH:mm") List<LocalTime> availableTime) {
}
