package com.example.Meeter.api.link;

import com.example.Meeter.api.link.dto.LinkDTO;
import com.example.Meeter.api.link.dto.LinkGenerationRequest;
import com.example.Meeter.core.link.service.LinkService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/link")
@RequiredArgsConstructor
public class LinkResource {
    private final LinkService linkService;

    @PostMapping
    @RolesAllowed("user")
    public LinkDTO generateLink(@RequestBody LinkGenerationRequest linkGenerationRequest) {
        return linkService.generateLink(linkGenerationRequest);
    }
}
