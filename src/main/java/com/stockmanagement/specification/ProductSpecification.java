package com.stockmanagement.specification;

import com.stockmanagement.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product>
    hasProductName(String productName) {

        return (root, query, cb) ->

                productName == null
                        ? null
                        : cb.like(
                        cb.lower(root.get("productName")),
                        "%" + productName.toLowerCase() + "%"
                );
    }

    public static Specification<Product>
    hasProductType(String productType) {

        return (root, query, cb) ->

                productType == null
                        ? null
                        : cb.equal(
                        cb.lower(root.get("productType")),
                        productType.toLowerCase()
                );
    }

    public static Specification<Product>
    hasMinRate(BigDecimal minRate) {

        return (root, query, cb) ->

                minRate == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                        root.get("rate"),
                        minRate
                );
    }

    public static Specification<Product>
    hasMaxRate(BigDecimal maxRate) {

        return (root, query, cb) ->

                maxRate == null
                        ? null
                        : cb.lessThanOrEqualTo(
                        root.get("rate"),
                        maxRate
                );
    }

    public static Specification<Product>
    belongsToTenant(Long tenantId) {

        return (root, query, cb) ->

                cb.equal(
                        root.get("tenantId"),
                        tenantId
                );
    }

    public static Specification<Product>
    isNotDeleted() {

        return (root, query, cb) ->

                cb.isFalse(root.get("deleted"));
    }
}