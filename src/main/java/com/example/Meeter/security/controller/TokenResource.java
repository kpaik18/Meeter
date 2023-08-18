package com.example.Meeter.security.controller;

import com.example.Meeter.security.auth.service.AuthService;
import com.example.Meeter.security.controller.dto.LoginDTO;
import com.example.Meeter.security.controller.dto.TokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@Validated
public class TokenResource {

    private final AuthService authService;

    @PostMapping
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/refresh")
    public TokenDTO refreshToken(@RequestBody @Valid TokenDTO tokenDTO) {
        return authService.refreshToken(tokenDTO);
    }

}
