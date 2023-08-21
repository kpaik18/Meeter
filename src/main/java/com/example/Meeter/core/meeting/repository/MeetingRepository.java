package com.example.Meeter.core.meeting.repository;

import com.example.Meeter.core.meeting.repository.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
