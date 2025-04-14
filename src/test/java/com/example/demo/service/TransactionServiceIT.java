package com.example.demo.service;

import com.example.demo.entity.Card;
import com.example.demo.entity.CardStatus;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionServiceIT {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    private TransactionService transactionService;

    private CardLimitService cardLimitService;
    private TransactionMapper transactionMapper;


    private Card senderCard;
    private Card receiverCard;

    @BeforeAll
    void setup() {
        cardLimitService = new CardLimitService(cardRepository);
        transactionService = new TransactionServiceImpl(transactionRepository, cardRepository, cardLimitService, transactionMapper);

        senderCard = Card.builder()
                .id(UUID.randomUUID())
                .dailyLimit(new BigDecimal("1000"))
                .remainingDailyLimit(new BigDecimal("1000"))
                .balance(new BigDecimal("500"))
                .status(CardStatus.ACTIVE)
                .build();

        receiverCard = Card.builder()
                .id(UUID.randomUUID())
                .dailyLimit(new BigDecimal("1000"))
                .remainingDailyLimit(new BigDecimal("1000"))
                .balance(new BigDecimal("100"))
                .status(CardStatus.ACTIVE)
                .build();

        testEntityManager.persist(senderCard);
        testEntityManager.persist(receiverCard);
    }

    @Test
    void testTransfer_successful() {
        transactionService.transfer(senderCard.getId(), receiverCard.getId(), new BigDecimal("300"), "Оплата услуг");

        Card updatedSenderCard = cardRepository.findById(senderCard.getId()).get();
        Card updatedReceiverCard = cardRepository.findById(receiverCard.getId()).get();

        assertEquals(new BigDecimal("200.00"), updatedSenderCard.getBalance());
        assertEquals(new BigDecimal("400.00"), updatedReceiverCard.getBalance());

        List<Transaction> transactions = transactionRepository.findAll();
        assertEquals(2, transactions.size());
    }
}
