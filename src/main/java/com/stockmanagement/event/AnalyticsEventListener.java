package com.stockmanagement.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnalyticsEventListener {

    @Async
    @EventListener
    public void handleProductCreatedAnalytics(
            ProductCreatedEvent event
    ) {

        log.info(
                "Analytics updated for product: {}",
                event.getProductName()
        );
    }
}