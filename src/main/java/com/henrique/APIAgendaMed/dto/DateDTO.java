package com.henrique.APIAgendaMed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DateDTO(@NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime date) {
}
