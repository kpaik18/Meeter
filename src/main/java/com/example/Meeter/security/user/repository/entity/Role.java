package com.example.Meeter.security.user.repository.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role")
    @SequenceGenerator(name = "role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1000)
    private Long id;
    private String code;
    private String name;
    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();
}
