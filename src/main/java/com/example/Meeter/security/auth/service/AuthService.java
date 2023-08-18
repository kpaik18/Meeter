package com.example.Meeter.security.auth.service;

import com.example.Meeter.exception.BusinessException;
import com.example.Meeter.exception.SecurityViolationException;
import com.example.Meeter.security.auth.user.UserRepository;
import com.example.Meeter.security.controller.dto.LoginDTO;
import com.example.Meeter.security.controller.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private UserDetails lookupUser(String username) {
        Optional<UserDetails> userDetailsOptional = userRepository.findByEmail(username);
        if (userDetailsOptional.isEmpty()) {
            throw new BusinessException("username_or_password_is_invalid");
        }
        return userDetailsOptional.get();
    }

    public TokenDTO login(LoginDTO loginDTO) {
        String username = loginDTO.username();
        String password = loginDTO.password();
        UserDetails user = lookupUser(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("username_or_password_is_invalid");
        }
        return new TokenDTO(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user));
    }

    public TokenDTO refreshToken(TokenDTO tokenDTO) {
        String username = jwtService.extractUsername(tokenDTO.refreshToken());
        if (!jwtService.isTokenValid(tokenDTO.refreshToken())) {
            throw new SecurityViolationException();
        }
        UserDetails user = lookupUser(username);
        return new TokenDTO(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }
}
