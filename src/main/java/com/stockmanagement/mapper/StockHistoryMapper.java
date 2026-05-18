package com.stockmanagement.mapper;

import com.stockmanagement.dto.StockHistoryResponseDto;
import com.stockmanagement.entity.StockHistory;

public class StockHistoryMapper {

    public static StockHistoryResponseDto mapToStockHistoryResponseDto(StockHistory stockHistory) {

        return StockHistoryResponseDto.builder()
                .id(stockHistory.getId())
                .operationType(stockHistory.getOperationType())
                .quantityChanged(stockHistory.getQuantityChanged())
                .previousQuantity(stockHistory.getPreviousQuantity())
                .newQuantity(stockHistory.getNewQuantity())
                .operationTime(stockHistory.getOperationTime())
                .productId(stockHistory.getProduct().getId())
                .productName(stockHistory.getProduct().getProductName())
                .productCode(stockHistory.getProduct().getProductCode())
                .build();
    }
}
