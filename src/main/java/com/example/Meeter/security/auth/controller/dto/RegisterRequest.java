package com.example.Meeter.security.auth.controller.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String password
) {
}
