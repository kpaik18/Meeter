package com.example.Meeter.security.controller.dto;

import jakarta.validation.constraints.NotNull;

public record TokenDTO(String accessToken,
                       @NotNull String refreshToken) {
}
