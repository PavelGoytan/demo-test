package com.example.demo.controller;


import com.example.demo.controller.dto.LimitRequest;
import com.example.demo.service.CardLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/cards/{cardId}/limits")
@RequiredArgsConstructor
public class AdminLimitController {
    private final CardLimitService cardLimitService;

    @PatchMapping
    public ResponseEntity<Void> updateDailyLimit(@PathVariable UUID cardId, @RequestBody LimitRequest limitRequest) {
        cardLimitService.updateLimit(cardId, limitRequest.getDailyLimit());
        return ResponseEntity.ok().build();
    }
}
