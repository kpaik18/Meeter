package com.example.Meeter.core.meeting.repository;

import com.example.Meeter.core.meeting.repository.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findAllByStartBetween(LocalDateTime start, LocalDateTime end);

    default List<Meeting> getAllDayMeetings(LocalDate day) {
        return findAllByStartBetween(day.atStartOfDay(), day.atStartOfDay().plusDays(1L));
    }
}
