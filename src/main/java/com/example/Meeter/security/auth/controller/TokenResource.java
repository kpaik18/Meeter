package com.example.Meeter.security.auth.controller;

import com.example.Meeter.security.auth.controller.dto.LoginDTO;
import com.example.Meeter.security.auth.controller.dto.RegisterRequest;
import com.example.Meeter.security.auth.controller.dto.TokenDTO;
import com.example.Meeter.security.auth.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class TokenResource {

    private final AuthService authService;

    @PostMapping("/token")
    @PermitAll
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/token/refresh")
    @PermitAll
    public TokenDTO refreshToken(@RequestBody @Valid TokenDTO tokenDTO) {
        return authService.refreshToken(tokenDTO);
    }

    @PostMapping("/register")
    @PermitAll
    public TokenDTO register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}
