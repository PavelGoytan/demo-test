package com.example.demo.service.impl;

import com.example.demo.controller.dto.CardRequest;
import com.example.demo.controller.dto.CardResponse;
import com.example.demo.entity.Card;
import com.example.demo.entity.CardStatus;
import com.example.demo.entity.User;
import com.example.demo.mapper.CardMapper;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {


    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TextEncryptor textEncryptor;
    private final CardMapper cardMapper;

    @Override
    public Card createCard(CardRequest req, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Card card = Card.builder()
                .encryptedCardNumber(textEncryptor.encrypt(req.getCardNumber()))
                .cardHolder(req.getCardHolder())
                .expirationDate(req.getExpirationDate())
                .status(CardStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .user(user)
                .build();

        return cardRepository.save(card);
    }

    @Override
    public void updateCardStatus(UUID cardId, CardStatus status) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(status);
        cardRepository.save(card);
    }

    @Override
    public Page<CardResponse> getUserCards(UUID userId, Pageable pageable) {
        return cardRepository.findByUserId(userId, pageable)
                .map(cardMapper::fromEntity);
    }

    @Override
    public void deleteCard(UUID cardId) {
        cardRepository.deleteById(cardId);
    }

    @Override
    public CardResponse getCardDetails(UUID cardId, UUID userId) {
        Card card = cardRepository.findById(cardId)
                .filter(c -> c.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Card not found or does not belong to user"));

        return cardMapper.fromEntity(card);
    }
}
