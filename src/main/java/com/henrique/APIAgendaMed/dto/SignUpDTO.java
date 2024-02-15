package com.henrique.APIAgendaMed.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpDTO(@NotNull String name,
                        @NotNull String password,
                        @NotNull @Email String email) {
}
