package com.example.Meeter.core.link.service;

import com.example.Meeter.core.link.controller.dto.LinkDTO;
import com.example.Meeter.core.link.controller.dto.LinkGenerationRequest;
import com.example.Meeter.core.link.repository.LinkRepository;
import com.example.Meeter.core.link.repository.entity.Link;
import com.example.Meeter.exception.BusinessException;
import com.example.Meeter.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

    private final UserService userService;

    public LinkDTO generateLink(LinkGenerationRequest linkGenerationRequest) {
        Link link = new Link();
        link.setUser(userService.getCurrentUser());
        LocalDateTime now = LocalDateTime.now();
        link.setStartValid(now);
        link.setEndValid(now.plus(linkGenerationRequest.days(), ChronoUnit.DAYS));
        link.setStartRange(linkGenerationRequest.startRange().atStartOfDay());
        link.setEndRange(linkGenerationRequest.endRange().atStartOfDay());
        link.setLink(RandomLinkGenerator.generateRandomString());
        linkRepository.save(link);
        return new LinkDTO(link.getLink());
    }

    public Link getLink(String link) {
        Optional<Link> linkOptional = linkRepository.findByLink(link);
        if (linkOptional.isEmpty()) {
            throw new BusinessException("link_not_valid");
        }
        Link dbLink = linkOptional.get();
        if (dbLink.getEndValid().isBefore(LocalDateTime.now())) {
            throw new BusinessException("link_not_valid");
        }
        return dbLink;
    }
}
