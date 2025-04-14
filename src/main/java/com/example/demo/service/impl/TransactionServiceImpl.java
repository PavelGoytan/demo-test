package com.example.demo.service.impl;

import com.example.demo.controller.dto.TransactionResponse;
import com.example.demo.entity.Card;
import com.example.demo.entity.CardStatus;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.CardLimitService;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final CardLimitService cardLimitService;
    private final TransactionMapper transactionMapper;

    @Transactional
    public void transfer(UUID fromCardId, UUID toCardId, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Сумма перевода должна быть положительной");
        }

        Card fromCard = cardRepository.findById(fromCardId)
                .orElseThrow(() -> new RuntimeException("Карта отправителя не найдена"));

        Card toCard = cardRepository.findById(toCardId)
                .orElseThrow(() -> new RuntimeException("Карта получателя не найдена"));

        if (fromCard.getStatus() != CardStatus.ACTIVE) {
            throw new RuntimeException("Карта отправителя неактивна");
        }

        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточный баланс");
        }

        if (!cardLimitService.validateAndReduceLimit(fromCardId, amount)) {
            throw new RuntimeException("Дневной лимит превышен");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));
        cardRepository.saveAll(List.of(fromCard, toCard));

        Transaction debitTransaction = Transaction.builder()
                .timestamp(LocalDateTime.now())
                .amount(amount.negate())
                .description(description)
                .card(fromCard)
                .build();

        Transaction creditTransaction = Transaction.builder()
                .timestamp(LocalDateTime.now())
                .amount(amount)
                .description(description)
                .card(toCard)
                .build();

        transactionRepository.saveAll(List.of(debitTransaction, creditTransaction));
    }

    @Override
    public Page<TransactionResponse> getCardTransactions(UUID cardId, UUID userId, Pageable pageable) {
        Card card = cardRepository.findById(cardId)
                .filter(c -> c.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Карта не найдена или принадлежит другому пользователю"));

        return transactionRepository.findByCardId(card.getId(), pageable)
                .map(transactionMapper::fromEntity);
    }
}
