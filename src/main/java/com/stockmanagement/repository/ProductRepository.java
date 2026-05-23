package com.stockmanagement.repository;

import com.stockmanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Page<Product> findByProductNameContainingIgnoreCase(
            String productName,
            Pageable pageable
    );

    Page<Product> findByProductCodeContainingIgnoreCase(
            String productCode,
            Pageable pageable
    );

    Page<Product> findByTenantIdAndDeletedFalse(
            Long tenantId,
            Pageable pageable
    );

    Optional<Product> findByIdAndTenantIdAndDeletedFalse(
            Long id,
            Long tenantId
    );

    Page<Product> findByProductNameContainingIgnoreCaseAndTenantIdAndDeletedFalse(
            String productName,
            Long tenantId,
            Pageable pageable
    );

    // Count only non-deleted products
    long countByDeletedFalse();

    // Count products with quantity below threshold
    // CORRECT — field is 'deleted' not 'isDeleted'
    long countByQuantityLessThanAndDeletedFalse(int quantity);
}
