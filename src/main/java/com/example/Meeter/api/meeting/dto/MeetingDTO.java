package com.example.Meeter.api.meeting.dto;

import com.example.Meeter.core.meeting.repository.entity.MeetingStatus;

import java.time.LocalDateTime;

public record MeetingDTO(Long id,
                         UserDTO owner,
                         UserDTO participant,
                         String name,
                         MeetingStatus status,
                         LocalDateTime start,
                         LocalDateTime end) {
}
