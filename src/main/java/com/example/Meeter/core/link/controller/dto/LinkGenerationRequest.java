package com.example.Meeter.core.link.controller.dto;

import java.time.LocalDate;

public record LinkGenerationRequest(
        int days,
        LocalDate startRange,
        LocalDate endRange
) {
}
