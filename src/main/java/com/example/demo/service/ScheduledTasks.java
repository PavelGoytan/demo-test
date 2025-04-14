package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final CardLimitService cardLimitService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetLimitsEveryMidnight() {
        cardLimitService.resetDailyLimit();
    }
}
