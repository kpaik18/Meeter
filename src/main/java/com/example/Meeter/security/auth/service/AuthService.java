package com.example.Meeter.security.auth.service;

import com.example.Meeter.exception.BusinessException;
import com.example.Meeter.exception.SecurityViolationException;
import com.example.Meeter.security.auth.controller.dto.LoginDTO;
import com.example.Meeter.security.auth.controller.dto.RegisterRequest;
import com.example.Meeter.security.auth.controller.dto.TokenDTO;
import com.example.Meeter.security.user.repository.UserRepository;
import com.example.Meeter.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public TokenDTO login(LoginDTO loginDTO) {
        String username = loginDTO.username();
        String password = loginDTO.password();
        UserDetails user = userService.lookupUserDetails(username);
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
        UserDetails user = userService.lookupUserDetails(username);
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
