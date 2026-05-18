package com.stockmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.bridge.IMessage;
import jakarta.persistence.Index;
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_name", columnList = "product_name"),
        @Index(name = "idx_product_code", columnList = "product_code"),
        @Index(name = "idx_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_deleted", columnList = "deleted")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(nullable = false)
    private Long tenantId;

    @Builder.Default
    private Boolean deleted = false;
}
