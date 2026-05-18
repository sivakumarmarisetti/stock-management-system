package com.stockmanagement.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductCleanupScheduler {

    @Scheduled(fixedRate = 60000)
    public void cleanupJob() {

        log.info("Running scheduled cleanup job...");
    }
}