package com.stockmanagement.mapper;

import com.stockmanagement.dto.ProductResponseDto;
import com.stockmanagement.entity.Product;

public class ProductMapper {

    public static ProductResponseDto mapToProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productType(product.getProductType())
                .rate(product.getRate())
                .volume(product.getVolume())
                .quantity(product.getQuantity())
                .build();

    }
}
