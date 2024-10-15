package com.example.meeter.core.meeting.controller;

import com.example.meeter.core.meeting.controller.dto.DayDTO;
import com.example.meeter.core.meeting.controller.dto.MeetingDTO;
import com.example.meeter.core.meeting.controller.dto.RepeaterDTO;
import com.example.meeter.core.meeting.service.MeetingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/meeting")
@RequiredArgsConstructor
@Validated
public class APIMeetingResource {
    private final MeetingService meetingService;

    @PostMapping
    @RolesAllowed("user")
    public void createMeeting(@RequestBody @Valid MeetingDTO meetingDTO) {
        meetingService.createMeeting(meetingDTO);
    }

    @GetMapping("/day")
    @RolesAllowed("user")
    public DayDTO getMeetingDay(@RequestParam(value = "date") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate day) {
        return meetingService.getMeetingDayForCurrentUser(day);
    }

    @GetMapping("/date-range")
    @RolesAllowed("user")
    public List<DayDTO> getMeetingDayRange(@RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd/MM/yyyy")
                                           LocalDate startDate,
                                           @RequestParam(value = "endDate") @DateTimeFormat(pattern = "dd/MM/yyyy")
                                           LocalDate endDate) {
        return meetingService.getMeetingDayRangeForCurrentUser(startDate, endDate);
    }

    @PostMapping("/repeater")
    @RolesAllowed("user")
    public void createRepeater(@RequestBody @Valid RepeaterDTO repeaterDTO) {
        meetingService.createRepeater(repeaterDTO);
    }

}
