package com.example.demo.service;

import com.example.demo.entity.Card;
import com.example.demo.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardLimitServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardLimitService cardLimitService;

    @Test
    void testValidateAndReduceLimit_success() {
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        Card card = Card.builder()
                .id(cardId)
                .dailyLimit(BigDecimal.valueOf(500))
                .remainingDailyLimit(BigDecimal.valueOf(200))
                .build();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        boolean result = cardLimitService.validateAndReduceLimit(cardId, amount);

        assertTrue(result);
        assertEquals(BigDecimal.valueOf(100), card.getRemainingDailyLimit());
        verify(cardRepository).save(card);
    }

    @Test
    void testValidateAndReduceLimit_fail_insufficientLimit() {
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(300);

        Card card = Card.builder()
                .id(cardId)
                .dailyLimit(BigDecimal.valueOf(500))
                .remainingDailyLimit(BigDecimal.valueOf(200))
                .build();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        boolean result = cardLimitService.validateAndReduceLimit(cardId, amount);

        assertFalse(result);
        verify(cardRepository, never()).save(any());
    }
}