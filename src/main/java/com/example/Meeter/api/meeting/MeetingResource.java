package com.example.Meeter.api.meeting;

import com.example.Meeter.api.meeting.dto.DayDTO;
import com.example.Meeter.api.meeting.dto.MeetingDTO;
import com.example.Meeter.core.meeting.service.MeetingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/meeting")
@RequiredArgsConstructor
@Validated
public class MeetingResource {
    private final MeetingService meetingService;

    @PostMapping
    @RolesAllowed("user")
    public void createMeeting(@RequestBody @Valid MeetingDTO meetingDTO) {
        meetingService.createMeeting(meetingDTO);
    }

    @GetMapping("/day")
    @RolesAllowed("user")
    public DayDTO getMeetingDay(@RequestParam("day") LocalDate day) {
        return meetingService.getMeetingDay(day);
    }
}
