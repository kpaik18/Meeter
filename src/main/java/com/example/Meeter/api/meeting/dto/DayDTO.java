package com.example.Meeter.api.meeting.dto;

import java.time.LocalDate;
import java.util.List;

public record DayDTO(LocalDate date,
                     List<MeetingDTO> meetings) {
}
