package com.example.Meeter.api.meeting;

import com.example.Meeter.api.meeting.dto.DayDTO;
import com.example.Meeter.core.meeting.service.MeetingService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reserve")
@RequiredArgsConstructor
public class ReserveResource {
    private final MeetingService meetingService;

    @GetMapping
    @RolesAllowed("user")
    public List<DayDTO> getOtherUsersDayByLink(String link) {
        return meetingService.getMeetingsByLink(link);
    }
}
