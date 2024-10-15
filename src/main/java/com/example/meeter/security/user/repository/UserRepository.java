package com.example.meeter.security.user.repository;

import com.example.meeter.security.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByEmail(String email);

    @Query("select u from User u where u.email = :email")
    User findUser(String email);

    boolean existsByEmail(String username);
}
