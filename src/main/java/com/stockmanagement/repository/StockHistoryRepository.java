package com.stockmanagement.repository;

import com.stockmanagement.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByProductId(Long productId);

    List<StockHistory> findByProductIdAndTenantId(
            Long productId,
            Long tenantId
    );

}
