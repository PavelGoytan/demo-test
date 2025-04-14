package com.example.demo.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequest {
    private String cardNumber;
    private String cardHolder;
    private LocalDate expirationDate;
}
