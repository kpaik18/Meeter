package com.example.meeter.core.meeting.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReserveMeetingRequest(
        @NotNull String link,
        @NotNull LocalDate date,
        Long meetingId,
        Long repeaterId
) {
    @AssertTrue
    @JsonIgnore
    public boolean isReserveMeetingValid() {
        return (meetingId == null && repeaterId != null) || (meetingId != null && repeaterId == null);
    }
}
