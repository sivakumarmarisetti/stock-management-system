package com.stockmanagement.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductCreatedEvent extends ApplicationEvent {

    private final String productName;

    public ProductCreatedEvent(Object source, String productName) {
        super(source);
        this.productName = productName;
    }
}