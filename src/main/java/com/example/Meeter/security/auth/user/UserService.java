package com.example.Meeter.security.auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserDetails> getUserDetailsByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

}
