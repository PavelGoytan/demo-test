package com.example.demo.controller;

import com.example.demo.controller.dto.TransactionResponse;
import com.example.demo.controller.dto.TransferRequest;
import com.example.demo.service.TransactionService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/cards")
@RequiredArgsConstructor
@Tag(name = "User Transactions", description = "Транзакции и переводы по картам")
public class UserTransactionController {
    private final TransactionService transactionService;


    @Operation(summary = "Перевод между картами")
    @ApiResponse(responseCode = "200", description = "Перевод успешно выполнен")
    @ApiResponse(responseCode = "400", description = "Ошибка выполнения перевода")
    @PostMapping("/{fromCardId}/transfer/{toCardId}")
    public ResponseEntity<Void> transfer(
            @PathVariable UUID fromCardId,
            @PathVariable UUID toCardId,
            @RequestBody TransferRequest request
    ) {
        transactionService.transfer(fromCardId, toCardId, request.getAmount(), request.getDescription());
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Просмотр транзакций по карте")
    @ApiResponse(responseCode = "200", description = "Успешное получение истории транзакций")
    @GetMapping("/{cardId}/transactions")
    public Page<TransactionResponse> getTransactions(
            @PathVariable UUID cardId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        return transactionService.getCardTransactions(cardId, userId, PageRequest.of(page, size));
    }
}
