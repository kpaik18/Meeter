package com.example.Meeter.core.meeting.controller.dto;

import com.example.Meeter.core.meeting.repository.entity.MeetingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MeetingDTO(Long id,
                         UserDTO owner,
                         UserDTO participant,
                         @NotNull String name,
                         MeetingStatus status,
                         @NotNull LocalDateTime start,
                         @NotNull LocalDateTime end,
                         Long repeaterId) {
    @AssertTrue
    @JsonIgnore
    public boolean isValidDates() {
        if (start.isAfter(end) || start.isEqual(end)) {
            return false;
        }
        return !start.isBefore(LocalDateTime.now());
    }
}
