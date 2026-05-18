package com.stockmanagement.service;

import com.stockmanagement.dto.ProductResponseDto;
import com.stockmanagement.dto.StockHistoryResponseDto;
import com.stockmanagement.dto.StockRequestDto;
import com.stockmanagement.entity.Product;
import com.stockmanagement.entity.StockHistory;
import com.stockmanagement.entity.StockOperationType;
import com.stockmanagement.exception.ResourceNotFoundException;
import com.stockmanagement.mapper.ProductMapper;
import com.stockmanagement.mapper.StockHistoryMapper;
import com.stockmanagement.repository.ProductRepository;
import com.stockmanagement.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.stockmanagement.security.TenantContext;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public ProductResponseDto addStock(StockRequestDto requestDto) {

//        Product product = getProductById(requestDto.getProductId());
        Product product = getTenantProduct(requestDto.getProductId());

        Integer previousQuantity = product.getQuantity();

        product.setQuantity(product.getQuantity() + requestDto.getQuantity());

        Product updatedProduct = productRepository.save(product);

        saveStockHistory(
                product,
                StockOperationType.ADD,
                requestDto.getQuantity(),
                previousQuantity,
                updatedProduct.getQuantity()
        );

        return ProductMapper.mapToProductResponseDto(updatedProduct);
    }

    public ProductResponseDto reduceStock(StockRequestDto requestDto) {

//        Product product = getProductById(requestDto.getProductId());
        Product product = getTenantProduct(requestDto.getProductId());

        Integer previousQuantity = product.getQuantity();

        if (previousQuantity < requestDto.getQuantity()) {
            throw new RuntimeException("Insufficient stock available");
        }

        product.setQuantity(product.getQuantity() - requestDto.getQuantity());

        Product updatedProduct = productRepository.save(product);

        saveStockHistory(
                product,
                StockOperationType.REDUCE,
                requestDto.getQuantity(),
                previousQuantity,
                updatedProduct.getQuantity()
        );

        return ProductMapper.mapToProductResponseDto(updatedProduct);
    }

    public List<StockHistoryResponseDto> getStockHistory(Long productId) {

        return stockHistoryRepository.findByProductIdAndTenantId(
                        productId,
                        TenantContext.getTenantId()
                )
                .stream()
                .map(StockHistoryMapper::mapToStockHistoryResponseDto)
                .toList();
    }

    private void saveStockHistory(
            Product product,
            StockOperationType operationType,
            Integer quantityChanged,
            Integer previousQuantity,
            Integer newQuantity) {

        StockHistory stockHistory = StockHistory.builder()
                .tenantId(TenantContext.getTenantId())
                .product(product)
                .operationType(operationType)
                .quantityChanged(quantityChanged)
                .previousQuantity(previousQuantity)
                .newQuantity(newQuantity)
                .operationTime(LocalDateTime.now())
                .build();

        stockHistoryRepository.save(stockHistory);
    }

    private Product getProductById(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));
    }

    private Product getTenantProduct(Long productId) {
        return productRepository
                .findByIdAndTenantIdAndDeletedFalse(
                        productId,
                        TenantContext.getTenantId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        ));
    }
}