package com.example.Meeter.core.link.repository.entity;

import com.example.Meeter.security.user.repository.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_link")
@Getter
@Setter
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link")
    @SequenceGenerator(name = "link", sequenceName = "seq_meeting_link", allocationSize = 1, initialValue = 1000)
    private Long id;
    @ManyToOne
    private User user;
    private String link;
    @Column(name = "start_valid")
    private LocalDateTime startValid;
    @Column(name = "end_valid")
    private LocalDateTime endValid;
    @Column(name = "start_range")
    private LocalDateTime startRange;
    @Column(name = "end_range")
    private LocalDateTime endRange;
}
