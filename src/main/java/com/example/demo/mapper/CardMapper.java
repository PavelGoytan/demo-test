package com.example.demo.mapper;

import com.example.demo.controller.dto.CardResponse;
import com.example.demo.controller.dto.LimitResponse;
import com.example.demo.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardMapper {
    public CardResponse fromEntity(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .maskedNumber("***" + card.getEncryptedCardNumber().substring(card.getEncryptedCardNumber().length() - 4))
                .cardHolder(card.getCardHolder())
                .expirationDate(card.getExpirationDate())
                .status(card.getStatus())
                .balance(card.getBalance())
                .build();
    }

    public LimitResponse limitFromEntity(Card card) {
        return LimitResponse.builder()
                .dailyLimit(card.getDailyLimit())
                .remainingDailyLimit(card.getRemainingDailyLimit())
                .build();
    }
}
