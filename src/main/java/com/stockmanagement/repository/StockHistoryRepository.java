package com.stockmanagement.repository;

import com.stockmanagement.entity.StockHistory;
import com.stockmanagement.entity.StockOperationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByProductId(Long productId);

    List<StockHistory> findByProductIdAndTenantId(
            Long productId,
            Long tenantId
    );

    long countByOperationType(StockOperationType operationType);

}
