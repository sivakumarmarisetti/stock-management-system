package com.stockmanagement.service;

import com.stockmanagement.dto.PaginatedResponseDto;
import com.stockmanagement.dto.ProductRequestDto;
import com.stockmanagement.dto.ProductResponseDto;
import com.stockmanagement.entity.Product;
import com.stockmanagement.exception.ResourceNotFoundException;
import com.stockmanagement.mapper.ProductMapper;
import com.stockmanagement.repository.ProductRepository;
import com.stockmanagement.security.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import com.stockmanagement.security.TenantContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import com.stockmanagement.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import com.stockmanagement.event.ProductCreatedEvent;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final NotificationService notificationService;
    private final ApplicationEventPublisher eventPublisher;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.builder()
                .tenantId(TenantContext.getTenantId())
                .productCode(requestDto.getProductCode())
                .productName(requestDto.getProductName())
                .productType(requestDto.getProductType())
                .rate(requestDto.getRate())
                .volume(requestDto.getVolume())
                .quantity(requestDto.getQuantity())
                .build();
        Product savedProduct = productRepository.save(product);
        eventPublisher.publishEvent(new ProductCreatedEvent(this, savedProduct.getProductName()));
        return ProductMapper.mapToProductResponseDto(savedProduct);
    }

    public PaginatedResponseDto<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findByTenantIdAndDeletedFalse(TenantContext.getTenantId(), pageable);
        List<ProductResponseDto> products = productPage.getContent()
                        .stream()
                        .map(ProductMapper::mapToProductResponseDto)
                        .toList();

        return PaginatedResponseDto
                .<ProductResponseDto>builder()
                .content(products)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    public PaginatedResponseDto<ProductResponseDto> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage =
                productRepository.findByProductNameContainingIgnoreCaseAndTenantIdAndDeletedFalse(
                                keyword,
                                TenantContext.getTenantId(),
                                pageable);
        List<ProductResponseDto> products =
                productPage.getContent()
                        .stream()
                        .map(ProductMapper::mapToProductResponseDto)
                        .toList();

        return PaginatedResponseDto
                .<ProductResponseDto>builder()
                .content(products)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    @Cacheable(value = "products", key = "#id + '_' + T(com.stockmanagement.security.TenantContext).getTenantId()")
    public ProductResponseDto getProductById(Long id) {
        Product product = getProductEntityById(id);
        return ProductMapper.mapToProductResponseDto(product);
    }

    @CachePut(value = "products", key = "#id + '_' + T(com.stockmanagement.security.TenantContext).getTenantId()")
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        Product existingProduct = getProductEntityById(id);

        existingProduct.setProductCode(requestDto.getProductCode());
        existingProduct.setProductName(requestDto.getProductName());
        existingProduct.setProductType(requestDto.getProductType());
        existingProduct.setRate(requestDto.getRate());
        existingProduct.setVolume(requestDto.getVolume());
        existingProduct.setQuantity(requestDto.getQuantity());
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.mapToProductResponseDto(updatedProduct);
    }

    @CacheEvict(value = "products", key = "#id + '_' + T(com.stockmanagement.security.TenantContext).getTenantId()")
    public void deleteProduct(Long id) {
        Product product = getProductEntityById(id);
        product.setDeleted(true);
        productRepository.save(product);
    }

    private Product getProductEntityById(Long id) {
        Long tenantId = TenantContext.getTenantId();
        return productRepository
                .findByIdAndTenantIdAndDeletedFalse(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    public PaginatedResponseDto<ProductResponseDto> filterProducts(String productName, String productType, BigDecimal minRate, BigDecimal maxRate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Product> specification = Specification
                        .where(ProductSpecification.hasProductName(productName))
                        .and(ProductSpecification.hasProductType(productType))
                        .and(ProductSpecification.hasMinRate(minRate))
                        .and(ProductSpecification.hasMaxRate(maxRate))
                        .and(ProductSpecification.belongsToTenant(TenantContext.getTenantId()))
                        .and(ProductSpecification.isNotDeleted());

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        List<ProductResponseDto> products =
                productPage.getContent()
                        .stream()
                        .map(ProductMapper::mapToProductResponseDto)
                        .toList();

        return PaginatedResponseDto
                .<ProductResponseDto>builder()
                .content(products)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }
}