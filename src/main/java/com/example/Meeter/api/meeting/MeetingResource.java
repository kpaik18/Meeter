package com.example.Meeter.api.meeting;

import com.example.Meeter.api.meeting.dto.DayDTO;
import com.example.Meeter.api.meeting.dto.MeetingDTO;
import com.example.Meeter.api.meeting.dto.RepeaterDTO;
import com.example.Meeter.core.meeting.service.MeetingService;
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
public class MeetingResource {
    private final MeetingService meetingService;

    @PostMapping
    @RolesAllowed("user")
    public void createMeeting(@RequestBody @Valid MeetingDTO meetingDTO) {
        meetingService.createMeeting(meetingDTO);
    }

    @GetMapping("/day")
    @RolesAllowed("user")
    public DayDTO getMeetingDay(@RequestParam(value = "date") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate day) {
        return meetingService.getMeetingDay(day);
    }

    @GetMapping("/date-range")
    @RolesAllowed("user")
    public List<DayDTO> getMeetingDayRange(@RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd/MM/yyyy")
                                           LocalDate startDate,
                                           @RequestParam(value = "endDate") @DateTimeFormat(pattern = "dd/MM/yyyy")
                                           LocalDate endDate) {
        return meetingService.getMeetingDayRange(startDate, endDate);
    }

    @PostMapping("/repeater")
    @RolesAllowed("user")
    public void createRepeater(@RequestBody @Valid RepeaterDTO repeaterDTO) {
        meetingService.createRepeater(repeaterDTO);
    }
}
