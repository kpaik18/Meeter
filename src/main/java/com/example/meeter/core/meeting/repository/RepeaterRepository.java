package com.example.meeter.core.meeting.repository;

import com.example.meeter.core.meeting.repository.entity.Repeater;
import com.example.meeter.security.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepeaterRepository extends JpaRepository<Repeater, Long> {
    List<Repeater> findAllByUser(User user);
}
