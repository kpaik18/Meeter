package com.example.Meeter.api.meeting.dto;

import com.example.Meeter.core.meeting.repository.entity.Meeting;
import com.example.Meeter.core.meeting.repository.entity.MeetingStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MeetingDTO(Long id,
                         UserDTO owner,
                         UserDTO participant,
                         @NotNull String name,
                         MeetingStatus status,
                         @NotNull LocalDateTime start,
                         @NotNull LocalDateTime end) {
    @AssertTrue
    public boolean isValidDates() {
        if (start.isAfter(end) || start.isEqual(end)) {
            return false;
        }
        return !start.isBefore(LocalDateTime.now());
    }
}
