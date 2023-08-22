package com.example.Meeter.core.meeting.service;

import com.example.Meeter.api.meeting.dto.DayDTO;
import com.example.Meeter.api.meeting.dto.MeetingDTO;
import com.example.Meeter.api.meeting.dto.RepeaterDTO;
import com.example.Meeter.api.meeting.dto.UserDTO;
import com.example.Meeter.core.meeting.repository.MeetingRepository;
import com.example.Meeter.core.meeting.repository.RepeaterRepository;
import com.example.Meeter.core.meeting.repository.entity.Meeting;
import com.example.Meeter.core.meeting.repository.entity.MeetingStatus;
import com.example.Meeter.core.meeting.repository.entity.Repeater;
import com.example.Meeter.core.meeting.repository.entity.WeekDay;
import com.example.Meeter.security.user.repository.entity.User;
import com.example.Meeter.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final UserService userService;

    private final MeetingRepository meetingRepository;

    private final RepeaterRepository repeaterRepository;

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
        User currentUser = userService.getCurrentUser();
        List<Meeting> dayMeetings = meetingRepository.getAllUserDayMeetingsOrderByStartDate(currentUser, day);
        List<Repeater> repeaters = repeaterRepository.findAllByUser(currentUser);

        List<MeetingDTO> meetingDTOS = meetingDTOListFromMeetings(day, dayMeetings);
        meetingDTOS.addAll(getRepeaterMeetingDTOsForLocalDate(day, repeaters, currentUser));
        meetingDTOS = orderMeetingDTOs(meetingDTOS);

        return new DayDTO(day, meetingDTOS);
    }

    public List<DayDTO> getMeetingDayRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();
        List<Meeting> dayMeetings = meetingRepository.getAllUserDayMeetingsInRangeOrderByStartDate(currentUser, startDate, endDate);
        List<Repeater> repeaters = repeaterRepository.findAllByUser(currentUser);
        List<DayDTO> result = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            LocalDate tmp = currentDate;
            List<Meeting> bucket = dayMeetings.stream().filter(m -> m.getStart().toLocalDate().equals(tmp)).toList();
            List<MeetingDTO> meetingDTOS = new ArrayList<>();
            if (bucket.size() > 0)
                meetingDTOS = meetingDTOListFromMeetings(bucket.get(0).getStart().toLocalDate(), bucket);
            meetingDTOS.addAll(getRepeaterMeetingDTOsForLocalDate(tmp, repeaters, currentUser));
            meetingDTOS = orderMeetingDTOs(meetingDTOS);
            result.add(new DayDTO(tmp, meetingDTOS));
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }

    private List<MeetingDTO> getRepeaterMeetingDTOsForLocalDate(LocalDate day, List<Repeater> repeaters, User currentUser) {
        List<MeetingDTO> result = new ArrayList<>();
        List<Repeater> dayRepeaters = repeaters.stream().filter(r -> r.getWeekDayList().contains(WeekDay.valueOf(day.getDayOfWeek().name()))).toList();
        for (var repeater : dayRepeaters) {
            result.add(new MeetingDTO(
                    null,
                    getUserDTOFromUser(currentUser),
                    null,
                    repeater.getName(),
                    MeetingStatus.OPEN,
                    day.atTime(repeater.getStartTime().toLocalTime()),
                    day.atTime(repeater.getEndTime().toLocalTime())
            ));
        }
        return result;
    }

    private List<MeetingDTO> orderMeetingDTOs(List<MeetingDTO> meetingDTOS) {
        meetingDTOS = meetingDTOS.stream().sorted((a, b) -> {
            if (a.start().isBefore(b.start())) {
                return -1;
            }
            if (a.start().equals(b.start())) {
                return 0;
            }
            return 1;
        }).toList();
        return meetingDTOS;
    }


    private List<MeetingDTO> meetingDTOListFromMeetings(LocalDate day, List<Meeting> meetings) {
        List<MeetingDTO> dayMeetingDTOs = new ArrayList<>();
        for (var meeting : meetings) {
            UserDTO owner = getUserDTOFromUser(meeting.getOwner());
            UserDTO participant = getUserDTOFromUser(meeting.getParticipant());
            dayMeetingDTOs.add(new MeetingDTO(meeting.getId(),
                    owner,
                    participant,
                    meeting.getName(),
                    meeting.getStatus(),
                    meeting.getStart(),
                    meeting.getEnd()));
        }
        return dayMeetingDTOs;
    }

    private UserDTO getUserDTOFromUser(User user) {
        if (user == null)
            return null;
        return new UserDTO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }


    public void createRepeater(RepeaterDTO dto) {
        Repeater repeater = new Repeater();
        repeater.setUser(userService.getCurrentUser());
        repeater.setName(dto.name());
        repeater.setStartTime(dto.startTime());
        repeater.setEndTime(dto.endTime());
        repeater.setWeekDayList(dto.weekDayList());
        repeaterRepository.save(repeater);
    }
}
