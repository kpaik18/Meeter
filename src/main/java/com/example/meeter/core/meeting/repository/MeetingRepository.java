package com.example.meeter.core.meeting.repository;

import com.example.meeter.core.meeting.repository.entity.Meeting;
import com.example.meeter.security.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("select m From Meeting m where (m.owner = :owner or m.participant = :owner) and m.start between :start and :end")
    List<Meeting> findAllByOwnerStartBetweenOrderByStart(User owner, LocalDateTime start, LocalDateTime end);

    default List<Meeting> getAllUserDayMeetingsOrderByStartDate(User user, LocalDate day) {
        return findAllByOwnerStartBetweenOrderByStart(user, day.atStartOfDay(), day.atStartOfDay().plusDays(1L));
    }

    default List<Meeting> getAllUserDayMeetingsInRangeOrderByStartDate(User user, LocalDate startDate, LocalDate endDate) {
        return findAllByOwnerStartBetweenOrderByStart(user, startDate.atStartOfDay(), endDate.atStartOfDay());
    }

    @Query("select exists (select 1 from Meeting m where m.repeater.id = :repeaterId and m.owner = :user and Date(m.start) = :reserveDate)")
    boolean existsMeetingOnRepeaterOnDate(LocalDate reserveDate, Long repeaterId, User user);
}
