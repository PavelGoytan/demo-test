package com.example.demo.controller;

import com.example.demo.controller.dto.CardResponse;
import com.example.demo.entity.CardStatus;
import com.example.demo.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/cards")
@RequiredArgsConstructor
@Tag(name = "User Cards", description = "Операции с картами пользователя")
public class UserCardController {
    private final CardService cardService;

    @Operation(summary = "Получение списка карт пользователя")
    @ApiResponse(responseCode = "200", description = "Успешное получение списка карт")
    @GetMapping
    public Page<CardResponse> getMyCards(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam(defaultValue="0") int page,
                                         @RequestParam(defaultValue="10") int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        return cardService.getUserCards(userId, PageRequest.of(page, size));
    }

    @Operation(summary = "Получение деталей карты пользователя")
    @ApiResponse(responseCode = "200", description = "Успешно получены данные о карте")
    @ApiResponse(responseCode = "404", description = "Карта не найдена")
    @GetMapping("/{cardId}")
    public CardResponse getCardDetails(@PathVariable UUID cardId, @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        return cardService.getCardDetails(cardId, userId);
    }

    @PatchMapping("/{id}/request-block")
    public ResponseEntity<Void> requestBlock(@PathVariable UUID id) {
        cardService.updateCardStatus(id, CardStatus.BLOCKED);
        return ResponseEntity.ok().build();
    }
}
