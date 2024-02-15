package com.henrique.APIAgendaMed.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull @Email String email, @NotNull String password) {
}
