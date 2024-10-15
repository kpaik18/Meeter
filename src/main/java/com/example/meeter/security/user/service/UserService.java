package com.example.meeter.security.user.service;

import com.example.meeter.exception.BusinessException;
import com.example.meeter.security.auth.controller.dto.RegisterRequest;
import com.example.meeter.security.user.repository.RoleRepository;
import com.example.meeter.security.user.repository.UserRepository;
import com.example.meeter.security.user.repository.entity.Role;
import com.example.meeter.security.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private static final String USER_ROLE_CODE = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
        Role role = roleRepository.findByCode(USER_ROLE_CODE);
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return lookupUser(username);
    }
}
