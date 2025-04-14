package com.example.demo.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class LimitRequest {
    private BigDecimal dailyLimit;
}
