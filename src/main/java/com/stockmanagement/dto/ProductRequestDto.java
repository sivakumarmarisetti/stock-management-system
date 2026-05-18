package com.stockmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String productType;

    @NotNull(message = "Rate is required")
    private Double rate;

    private Double volume;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}
