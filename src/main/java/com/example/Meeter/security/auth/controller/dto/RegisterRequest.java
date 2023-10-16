package com.example.Meeter.security.auth.controller.dto;

import com.example.Meeter.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull @Size(min = 3) String firstName,
        @NotNull @Size(min = 3) String lastName,
        @NotNull @Pattern(regexp = RegexPatterns.EMAIL_PATTERN) String email,
        @NotNull @Size(min = 3) String password
) {
}
