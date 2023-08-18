package com.example.Meeter.security.controller.dto;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String username,
                       @NotNull String password) {
}
