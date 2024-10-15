package com.example.meeter.security.auth.controller.dto;

import jakarta.validation.constraints.NotNull;

public record TokenDTO(String accessToken,
                       @NotNull String refreshToken) {
}
