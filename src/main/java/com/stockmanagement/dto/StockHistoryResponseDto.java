package com.stockmanagement.dto;

import com.stockmanagement.entity.StockOperationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StockHistoryResponseDto {

    private Long id;
    private StockOperationType operationType;
    private Integer quantityChanged;
    private Integer previousQuantity;
    private Integer newQuantity;
    private LocalDateTime operationTime;

    private Long productId;
    private String productName;
    private String productCode;

}
