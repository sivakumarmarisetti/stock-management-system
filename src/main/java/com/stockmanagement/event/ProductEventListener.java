package com.stockmanagement.event;

import com.stockmanagement.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleProductCreatedEvent(ProductCreatedEvent event) {

        log.info("Received product created event for: {}", event.getProductName());

        notificationService.sendProductCreatedNotification(event.getProductName());
    }
}