package com.example.Meeter.core.meeting.service;

import com.example.Meeter.api.meeting.dto.*;
import com.example.Meeter.core.link.repository.entity.Link;
import com.example.Meeter.core.link.service.LinkService;
import com.example.Meeter.core.meeting.repository.MeetingRepository;
import com.example.Meeter.core.meeting.repository.RepeaterRepository;
import com.example.Meeter.core.meeting.repository.entity.Meeting;
import com.example.Meeter.core.meeting.repository.entity.MeetingStatus;
import com.example.Meeter.core.meeting.repository.entity.Repeater;
import com.example.Meeter.core.meeting.repository.entity.WeekDay;
import com.example.Meeter.exception.BusinessException;
import com.example.Meeter.exception.SecurityViolationException;
import com.example.Meeter.security.user.repository.entity.User;
import com.example.Meeter.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final UserService userService;

    private final MeetingRepository meetingRepository;

    private final RepeaterRepository repeaterRepository;

    private final LinkService linkService;

    public void createMeeting(MeetingDTO dto) {
        Meeting meeting = new Meeting();
        meeting.setOwner(userService.getCurrentUser());
        meeting.setName(dto.name());
        meeting.setStatus(MeetingStatus.OPEN);
        meeting.setStart(dto.start());
        meeting.setEnd(dto.end());
        meetingRepository.save(meeting);
    }

    private Meeting getMeeting(Long meetingId) {
        Optional<Meeting> meetingOptional = meetingRepository.findById(meetingId);
        if (meetingOptional.isEmpty()) {
            throw new SecurityViolationException();
        }
        return meetingOptional.get();
    }

    private Repeater getRepeater(Long repeaterId) {
        Optional<Repeater> repeaterOptional = repeaterRepository.findById(repeaterId);
        if (repeaterOptional.isEmpty()) {
            throw new SecurityViolationException();
        }
        return repeaterOptional.get();
    }

    public DayDTO getMeetingDayForCurrentUser(LocalDate day) {
        User currentUser = userService.getCurrentUser();
        List<Meeting> dayMeetings = meetingRepository.getAllUserDayMeetingsOrderByStartDate(currentUser, day);
        List<Repeater> repeaters = repeaterRepository.findAllByUser(currentUser);

        List<MeetingDTO> meetingDTOS = meetingDTOListFromMeetings(day, dayMeetings);
        meetingDTOS.addAll(getRepeaterMeetingDTOsForLocalDate(day, repeaters, currentUser));
        meetingDTOS = orderMeetingDTOs(meetingDTOS);

        return new DayDTO(day, meetingDTOS);
    }

    public List<DayDTO> getMeetingDayRangeForCurrentUser(LocalDate startDate, LocalDate endDate) {
        return getMeetingDayRange(startDate, endDate, userService.getCurrentUser());
    }

    public List<DayDTO> getMeetingDayRange(LocalDate startDate, LocalDate endDate, User user) {
        List<Meeting> dayMeetings = meetingRepository.getAllUserDayMeetingsInRangeOrderByStartDate(user, startDate, endDate);
        List<Repeater> repeaters = repeaterRepository.findAllByUser(user);
        List<DayDTO> result = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            LocalDate tmp = currentDate;
            List<Meeting> bucket = dayMeetings.stream().filter(m -> m.getStart().toLocalDate().equals(tmp)).toList();
            List<MeetingDTO> meetingDTOS = new ArrayList<>();
            if (bucket.size() > 0)
                meetingDTOS = meetingDTOListFromMeetings(bucket.get(0).getStart().toLocalDate(), bucket);
            meetingDTOS.addAll(getRepeaterMeetingDTOsForLocalDate(tmp, repeaters, user));
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
                    day.atTime(repeater.getEndTime().toLocalTime()),
                    repeater.getId()
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
                    meeting.getEnd(),
                    null));
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

    public List<DayDTO> getMeetingsByLink(String link) {
        Link dbLink = linkService.getLink(link);
        return getMeetingDayRange(
                dbLink.getStartRange().toLocalDate(),
                dbLink.getEndRange().toLocalDate(),
                dbLink.getUser());
    }

    public void reserveOtherUsersMeeting(ReserveMeetingRequest reserveMeetingRequest) {
        Link link = linkService.getLink(reserveMeetingRequest.link());
        LocalDate reserveDate = reserveMeetingRequest.date();
        if (reserveDate.isBefore(link.getStartRange().toLocalDate()) || reserveDate.isAfter(link.getEndRange().toLocalDate())) {
            throw new BusinessException("reserve_date_is_invalid_for_link");
        }
        if (reserveMeetingRequest.meetingId() != null) {
            reserveMeetingByMeetingId(reserveMeetingRequest);
        }
        if (reserveMeetingRequest.repeaterId() != null) {
            reserveMeetingByRepeaterId(reserveMeetingRequest);
        }
    }

    private void reserveMeetingByRepeaterId(ReserveMeetingRequest reserveMeetingRequest) {
        LocalDate reserveDate = reserveMeetingRequest.date();
        Long repeaterId = reserveMeetingRequest.repeaterId();
        Repeater repeater = getRepeater(repeaterId);
        String reserveDateWeekDay = String.valueOf(reserveDate.getDayOfWeek());
        Set<WeekDay> repeaterDays = repeater.getWeekDayList();
        if (!repeaterDays.contains(WeekDay.valueOf(reserveDateWeekDay))) {
            throw new BusinessException("invalid_day_for_repeater_meeting");
        }
        Meeting meetingByRepeater = new Meeting();
        meetingByRepeater.setName(repeater.getName());
        meetingByRepeater.setOwner(repeater.getUser());
        meetingByRepeater.setStart(reserveDate.atTime(repeater.getStartTime().toLocalTime()));
        meetingByRepeater.setEnd(reserveDate.atTime(repeater.getEndTime().toLocalTime()));
        meetingByRepeater.setParticipant(userService.getCurrentUser());
        meetingByRepeater.setStatus(MeetingStatus.RESERVED);
        meetingByRepeater.setRepeater(repeater);
        meetingRepository.save(meetingByRepeater);
    }

    private void reserveMeetingByMeetingId(ReserveMeetingRequest reserveMeetingRequest) {
        Meeting meeting = getMeeting(reserveMeetingRequest.meetingId());
        if (meeting.getStatus().equals(MeetingStatus.RESERVED)) {
            throw new BusinessException("meeting_already_reserved");
        }
        if (!meeting.getStart().toLocalDate().equals(reserveMeetingRequest.date())) {
            throw new BusinessException("meeting_date_not_valid");
        }
        meeting.setStatus(MeetingStatus.RESERVED);
    }
}
