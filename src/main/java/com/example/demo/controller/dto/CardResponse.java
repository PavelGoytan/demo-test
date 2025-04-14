package com.example.demo.controller.dto;

import com.example.demo.entity.CardStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CardResponse {
    private UUID id;
    private String maskedNumber;
    private String cardHolder;
    private LocalDate expirationDate;
    private CardStatus status;
    private BigDecimal balance;
}
