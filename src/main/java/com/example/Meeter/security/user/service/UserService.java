package com.example.Meeter.security.user.service;

import com.example.Meeter.exception.BusinessException;
import com.example.Meeter.security.auth.controller.dto.RegisterRequest;
import com.example.Meeter.security.user.repository.UserRepository;
import com.example.Meeter.security.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User lookupUser(String username) {
        User user = userRepository.findUser(username);
        if (user == null) {
            throw new BusinessException("user_not_found");
        }
        return user;
    }

    public boolean existsUsername(String username) {
        return userRepository.existsByEmail(username);
    }

    public void register(RegisterRequest registerRequest) {
        if (existsUsername(registerRequest.email())) {
            throw new BusinessException("email_already_exists");
        }
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(user);
    }

    public Collection<? extends GrantedAuthority> getUserAuthorities(String userEmail) {
        User user = lookupUser(userEmail);
        return user.getAuthorities();
    }
}
