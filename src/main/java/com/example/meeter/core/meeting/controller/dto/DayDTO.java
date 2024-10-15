package com.example.meeter.core.meeting.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record DayDTO(LocalDate date,
                     List<MeetingDTO> meetings) {
}
