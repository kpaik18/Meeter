package com.example.Meeter.core.meeting.repository.entity;

import com.example.Meeter.security.user.repository.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@Getter
@Setter
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting")
    @SequenceGenerator(name = "meeting", sequenceName = "seq_meeting", allocationSize = 1, initialValue = 1000)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;
    private String name;
    @Enumerated(EnumType.STRING)
    private MeetingStatus status;
    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime end;
    @ManyToOne
    private Repeater repeater;
}
