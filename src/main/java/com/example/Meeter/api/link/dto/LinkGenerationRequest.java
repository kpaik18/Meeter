package com.example.Meeter.api.link.dto;

import java.time.LocalDate;

public record LinkGenerationRequest(
        int days,
        LocalDate startRange,
        LocalDate endRange
) {
}
