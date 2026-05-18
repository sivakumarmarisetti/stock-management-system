package com.stockmanagement.service;

import com.stockmanagement.entity.Product;
import com.stockmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryMonitoringService {

    private final ProductRepository productRepository;

    private static final int LOW_STOCK_THRESHOLD = 10;

    @Scheduled(fixedRate = 60000)
    public void monitorLowStockProducts() {

        log.info("Running low stock monitoring job...");

        List<Product> products =
                productRepository.findAll();

        products.stream()
                .filter(product ->
                        !Boolean.TRUE.equals(
                                product.getDeleted()
                        )
                )
                .filter(product ->
                        product.getQuantity()
                                <= LOW_STOCK_THRESHOLD
                )
                .forEach(product ->

                        log.warn(
                                "LOW STOCK ALERT -> Product: {} | Quantity: {}",
                                product.getProductName(),
                                product.getQuantity()
                        )
                );

        log.info("Low stock monitoring completed.");
    }
}