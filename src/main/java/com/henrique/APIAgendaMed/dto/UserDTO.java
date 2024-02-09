package com.henrique.APIAgendaMed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public record UserDTO(String id, String name, @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime createdAt) {
}
