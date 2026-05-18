package com.stockmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @Async
    public void sendProductCreatedNotification(String productName) {

        try {

            log.info("Sending notification for product: {}", productName);

            Thread.sleep(5000);

            log.info("Notification sent successfully for product: {}", productName);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            log.error("Notification sending interrupted");
        }
    }
}