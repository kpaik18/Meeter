package com.example.Meeter.security.controller;

import com.example.Meeter.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class SessionResource {

    private final JwtService jwtService;

    @PostMapping
    public String getToken() {
        return jwtService.generateToken(
                new User("paikidzekoba@gmail.com", "", true, false, false, false, List.of()));
    }

}
