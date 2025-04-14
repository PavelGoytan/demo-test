package com.example.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal amount;
    private String description;
}
