package com.stockmanagement.controller;

import com.stockmanagement.dto.ApiResponse;
import com.stockmanagement.dto.ProductResponseDto;
import com.stockmanagement.dto.StockHistoryResponseDto;
import com.stockmanagement.dto.StockRequestDto;
import com.stockmanagement.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_OPERATOR')")
    @PostMapping("/add")
    public ApiResponse<ProductResponseDto> addStock(
            @Valid @RequestBody StockRequestDto requestDto) {

        ProductResponseDto product = stockService.addStock(requestDto);

        return ApiResponse.<ProductResponseDto>builder()
                .success(true)
                .message("Stock added successfully")
                .data(product)
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_OPERATOR')")
    @PostMapping("/reduce")
    public ApiResponse<ProductResponseDto> reduceStock(
            @Valid @RequestBody StockRequestDto requestDto) {

        ProductResponseDto product = stockService.reduceStock(requestDto);

        return ApiResponse.<ProductResponseDto>builder()
                .success(true)
                .message("Stock reduced successfully")
                .data(product)
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/history/{productId}")
    public ApiResponse<List<StockHistoryResponseDto>> getStockHistory(
            @PathVariable Long productId) {

        List<StockHistoryResponseDto> history =
                stockService.getStockHistory(productId);

        return ApiResponse.<List<StockHistoryResponseDto>>builder()
                .success(true)
                .message("Stock history fetched successfully")
                .data(history)
                .build();
    }
}