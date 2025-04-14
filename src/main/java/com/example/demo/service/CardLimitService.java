package com.example.demo.service;

import com.example.demo.entity.Card;
import com.example.demo.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardLimitService {
    private final CardRepository cardRepository;

    @Transactional
    public void updateLimit(UUID cardId, BigDecimal newLimit) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card Not Found"));
        card.setDailyLimit(newLimit);
        card.setRemainingDailyLimit(newLimit);
        cardRepository.save(card);
    }

    @Transactional
    public void resetDailyLimit() {
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards) {
            card.setRemainingDailyLimit(card.getDailyLimit());
        }
        cardRepository.saveAll(cards);
    }

    @Transactional
    public boolean validateAndReduceLimit(UUID cardId, BigDecimal amount) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card Not Found"));
        if (card.getRemainingDailyLimit().compareTo(amount) >= 0) {
            card.setRemainingDailyLimit(card.getRemainingDailyLimit().subtract(amount));
            cardRepository.save(card);
            return true;
        }
        return false;
    }
}
