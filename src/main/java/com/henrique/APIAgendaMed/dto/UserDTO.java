package com.henrique.APIAgendaMed.dto;

import java.util.Date;

public record UserDTO(Long id, String name, Date createdAt) {
}
