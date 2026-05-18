package com.stockmanagement.service;

import com.stockmanagement.dto.ProductRequestDto;
import com.stockmanagement.dto.ProductResponseDto;
import com.stockmanagement.entity.Product;
import com.stockmanagement.repository.ProductRepository;
import com.stockmanagement.security.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProductService productService;



    @BeforeEach
    void setup() {
        TenantContext.setTenantId(1L);
    }

    @Test
    void shouldCreateProductSuccessfully() {

        ProductRequestDto requestDto =
                ProductRequestDto.builder()
                        .productCode("APL-IP15")
                        .productName("iPhone 15")
                        .productType("Electronics")
                        .rate(90000.0)
                        .volume(10.0)
                        .quantity(50)
                        .build();

        Product savedProduct =
                Product.builder()
                        .id(1L)
                        .tenantId(1L)
                        .productCode("APL-IP15")
                        .productName("iPhone 15")
                        .productType("Electronics")
                        .rate(90000.0)
                        .volume(10.0)
                        .quantity(50)
                        .deleted(false)
                        .build();

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponseDto response =
                productService.createProduct(requestDto);

        assertNotNull(response);

        assertEquals(
                "APL-IP15",
                response.getProductCode()
        );

        assertEquals(
                "iPhone 15",
                response.getProductName()
        );

        verify(productRepository, times(1))
                .save(any(Product.class));
    }
}