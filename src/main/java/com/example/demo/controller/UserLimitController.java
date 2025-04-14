package com.example.demo.controller;

import com.example.demo.controller.dto.LimitResponse;
import com.example.demo.entity.Card;
import com.example.demo.mapper.CardMapper;
import com.example.demo.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/cards/{cardId}/limits")
@RequiredArgsConstructor
public class UserLimitController {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @GetMapping
    public ResponseEntity<LimitResponse> getLimit(@PathVariable UUID cardId, @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        Card card = cardRepository.findById(cardId)
                .filter(c -> c.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Card Not Found"));

        return ResponseEntity.ok(cardMapper.limitFromEntity(card));
    }
}
