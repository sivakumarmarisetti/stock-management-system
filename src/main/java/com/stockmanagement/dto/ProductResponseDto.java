package com.stockmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {

    private Long id;
    private String productCode;
    private String productName;
    private String productType;
    private Double rate;
    private Double volume;
    private Integer quantity;
}
