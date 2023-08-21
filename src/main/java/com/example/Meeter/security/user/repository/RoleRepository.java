package com.example.Meeter.security.user.repository;

import com.example.Meeter.security.user.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(String code);
}
