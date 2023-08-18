package com.example.Meeter.security.user.service;

import com.example.Meeter.exception.BusinessException;
import com.example.Meeter.security.auth.controller.dto.RegisterRequest;
import com.example.Meeter.security.user.repository.UserRepository;
import com.example.Meeter.security.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetails lookupUser(String username) {
        Optional<UserDetails> userDetailsOptional = userRepository.findByEmail(username);
        if (userDetailsOptional.isEmpty()) {
            throw new BusinessException("username_or_password_is_invalid");
        }
        return userDetailsOptional.get();
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
}
