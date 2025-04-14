package com.example.demo.service;

import com.example.demo.controller.dto.CardRequest;
import com.example.demo.controller.dto.CardResponse;
import com.example.demo.entity.Card;
import com.example.demo.entity.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CardService {
    Card createCard(CardRequest cardRequest, UUID userId);

    void updateCardStatus(UUID cardId, CardStatus status);

    Page<CardResponse> getUserCards(UUID userId, Pageable pageable);

    void deleteCard(UUID cardId);

    CardResponse getCardDetails(UUID cardId, UUID userId);
}
