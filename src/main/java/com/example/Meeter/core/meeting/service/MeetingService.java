package com.example.Meeter.core.meeting.service;

import com.example.Meeter.api.meeting.dto.DayDTO;
import com.example.Meeter.api.meeting.dto.MeetingDTO;
import com.example.Meeter.core.meeting.repository.MeetingRepository;
import com.example.Meeter.core.meeting.repository.entity.Meeting;
import com.example.Meeter.core.meeting.repository.entity.MeetingStatus;
import com.example.Meeter.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final UserService userService;

    private final MeetingRepository meetingRepository;

    public void createMeeting(MeetingDTO dto) {
        Meeting meeting = new Meeting();
        meeting.setOwner(userService.getCurrentUser());
        meeting.setName(dto.name());
        meeting.setStatus(MeetingStatus.OPEN);
        meeting.setStart(dto.start());
        meeting.setEnd(dto.end());
        meetingRepository.save(meeting);
    }

    public DayDTO getMeetingDay(LocalDate day) {
        List<Meeting> dayMeetings = meetingRepository.getAllDayMeetings(day);
//        List<Meeting> dayMeetingDTOs = dayMeetings.stream().map()
        return null;
    }
}
