package com.example.meeter.security.user.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permission")
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission")
    @SequenceGenerator(name = "permission", sequenceName = "seq_permission", allocationSize = 1, initialValue = 1000)
    private Long id;
    private String code;
    private String name;
}
