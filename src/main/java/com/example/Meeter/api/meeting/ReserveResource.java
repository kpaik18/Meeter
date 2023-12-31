package com.example.Meeter.api.meeting;

import com.example.Meeter.api.meeting.dto.DayDTO;
import com.example.Meeter.api.meeting.dto.ReserveMeetingRequest;
import com.example.Meeter.core.meeting.service.MeetingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reserve")
@RequiredArgsConstructor
@Validated
public class ReserveResource {
    private final MeetingService meetingService;

    @GetMapping
    @RolesAllowed("user")
    public List<DayDTO> getOtherUsersDayByLink(String link) {
        return meetingService.getMeetingsByLink(link);
    }

    @PostMapping("/bylink")
    @RolesAllowed("user")
    public void reserveOtherUsersMeetingByLink(@RequestBody @Valid ReserveMeetingRequest reserveMeetingRequest) {
        meetingService.reserveOtherUsersMeeting(reserveMeetingRequest);
    }

}
