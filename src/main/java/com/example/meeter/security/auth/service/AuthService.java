package com.example.meeter.security.auth.service;

import com.example.meeter.exception.BusinessException;
import com.example.meeter.exception.SecurityViolationException;
import com.example.meeter.security.auth.controller.dto.LoginDTO;
import com.example.meeter.security.auth.controller.dto.RegisterRequest;
import com.example.meeter.security.auth.controller.dto.TokenDTO;
import com.example.meeter.security.user.repository.entity.User;
import com.example.meeter.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public TokenDTO login(LoginDTO loginDTO) {
        String username = loginDTO.username();
        String password = loginDTO.password();
        User user = userService.lookupUser(username);
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
        User user = userService.lookupUser(username);
        return new TokenDTO(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    public TokenDTO register(RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return login(new LoginDTO(registerRequest.email(), registerRequest.password()));
    }
}
