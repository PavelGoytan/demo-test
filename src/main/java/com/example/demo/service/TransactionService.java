package com.example.demo.service;

import com.example.demo.controller.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {
    void transfer(UUID fromCardId, UUID toCardId, BigDecimal amount, String description);
    Page<TransactionResponse> getCardTransactions(UUID cardId, UUID userId, Pageable pageable);
}
