package com.example.Meeter.core.meeting.repository;

import com.example.Meeter.core.meeting.repository.entity.Meeting;
import com.example.Meeter.security.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findAllByOwnerAndStartBetweenOrderByStart(User owner, LocalDateTime start, LocalDateTime end);

    default List<Meeting> getAllUserDayMeetingsOrderByStartDate(User user, LocalDate day) {
        return findAllByOwnerAndStartBetweenOrderByStart(user, day.atStartOfDay(), day.atStartOfDay().plusDays(1L));
    }

    default List<Meeting> getAllUserDayMeetingsInRangeOrderByStartDate(User user, LocalDate startDate, LocalDate endDate) {
        return findAllByOwnerAndStartBetweenOrderByStart(user, startDate.atStartOfDay(), endDate.atStartOfDay());
    }

}
