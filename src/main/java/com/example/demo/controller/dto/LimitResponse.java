package com.example.demo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LimitResponse {
    private BigDecimal dailyLimit;
    private BigDecimal remainingDailyLimit;
}
