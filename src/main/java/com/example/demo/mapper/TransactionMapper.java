package com.example.demo.mapper;

import com.example.demo.controller.dto.TransactionResponse;
import com.example.demo.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public TransactionResponse fromEntity(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .timestamp(transaction.getTimestamp())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .build();
    }
}
