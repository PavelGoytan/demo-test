package com.example.demo.controller;

import com.example.demo.controller.dto.CardRequest;
import com.example.demo.controller.dto.CardResponse;
import com.example.demo.entity.Card;
import com.example.demo.entity.CardStatus;
import com.example.demo.mapper.CardMapper;
import com.example.demo.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/cards")
@RequiredArgsConstructor
public class AdminCardController {
    private final CardService cardService;
    private final CardMapper cardMapper;

    @PostMapping("/{userId}")
    public CardResponse createCard(@RequestBody CardRequest cardRequest, @PathVariable UUID userId) {
        Card card = cardService.createCard(cardRequest, userId);
        return cardMapper.fromEntity(card);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        cardService.updateCardStatus(id, CardStatus.ACTIVE);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<Void> block(@PathVariable UUID id) {
        cardService.updateCardStatus(id, CardStatus.BLOCKED);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
