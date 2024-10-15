package com.example.meeter.core.meeting.repository.entity;

import com.example.meeter.security.user.repository.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repeater")
@Getter
@Setter
public class Repeater {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repeater")
    @SequenceGenerator(name = "repeater", sequenceName = "seq_repeater", allocationSize = 1, initialValue = 1000)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    @Convert(converter = WeekDayListConverter.class)
    @Column(name = "weekdays")
    private Set<WeekDay> weekDayList = new HashSet<>();

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

}
