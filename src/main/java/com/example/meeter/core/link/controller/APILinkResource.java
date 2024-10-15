package com.example.meeter.core.link.controller;

import com.example.meeter.core.link.controller.dto.LinkDTO;
import com.example.meeter.core.link.controller.dto.LinkGenerationRequest;
import com.example.meeter.core.link.service.LinkService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/link")
@RequiredArgsConstructor
public class APILinkResource {
    private final LinkService linkService;

    @PostMapping
    @RolesAllowed("user")
    public LinkDTO generateLink(@RequestBody LinkGenerationRequest linkGenerationRequest) {
        return linkService.generateLink(linkGenerationRequest);
    }
}
