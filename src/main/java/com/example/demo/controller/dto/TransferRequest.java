package com.example.demo.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private BigDecimal amount;
    private String description;
}
