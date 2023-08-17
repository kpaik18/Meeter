package com.example.Meeter.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserDetailsByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }
}
