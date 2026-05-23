package com.stockmanagement.service;

import com.stockmanagement.dto.DashboardSummaryDto;
import com.stockmanagement.entity.StockOperationType;
import com.stockmanagement.repository.ProductRepository;
import com.stockmanagement.repository.StockHistoryRepository;
import com.stockmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public DashboardSummaryDto getSummary() {
        return DashboardSummaryDto.builder()
                .totalProducts(productRepository.countByDeletedFalse())
                .totalUsers(userRepository.count())
                .totalStockAdds(stockHistoryRepository.countByOperationType(StockOperationType.ADD))
                .totalStockReduces(stockHistoryRepository.countByOperationType(StockOperationType.REDUCE))
                .lowStockProducts(productRepository.countByQuantityLessThanAndDeletedFalse(10))
                .build();
    }
}