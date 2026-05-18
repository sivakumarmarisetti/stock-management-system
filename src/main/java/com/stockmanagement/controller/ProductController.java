package com.stockmanagement.controller;

import com.stockmanagement.dto.ApiResponse;
import com.stockmanagement.dto.ProductRequestDto;
import com.stockmanagement.dto.ProductResponseDto;
import com.stockmanagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.stockmanagement.dto.PaginatedResponseDto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Management", description = "APIs for managing products")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create Product", description = "Creates a new product for the current tenant")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {

        ProductResponseDto product = productService.createProduct(requestDto);

        return ApiResponse.<ProductResponseDto>builder().success(true).message("Product created successfully").data(product).build();
    }

    @Operation(summary = "Get All Products", description = "Fetch paginated products for current tenant")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<PaginatedResponseDto<ProductResponseDto>> getAllProducts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDirection) {

        PaginatedResponseDto<ProductResponseDto> products = productService.getAllProducts(page, size, sortBy, sortDirection);

        return ApiResponse.<PaginatedResponseDto<ProductResponseDto>>builder().success(true).message("Products fetched successfully").data(products).build();
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<PaginatedResponseDto<ProductResponseDto>> searchProducts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size) {

        PaginatedResponseDto<ProductResponseDto> products = productService.searchProducts(keyword, page, size);

        return ApiResponse.<PaginatedResponseDto<ProductResponseDto>>builder().success(true).message("Products fetched successfully").data(products).build();
    }


    @Operation(summary = "Get Product By ID", description = "Fetch single product details")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto product = productService.getProductById(id);
        return ApiResponse.<ProductResponseDto>builder().success(true).message("Product fetched successfully").data(product).build();
    }

    @Operation(summary = "Update Product", description = "Update existing product")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto updatedProduct = productService.updateProduct(id, requestDto);

        return ApiResponse.<ProductResponseDto>builder().success(true).message("Product updated successfully").data(updatedProduct).build();
    }

    @Operation(summary = "Delete Product", description = "Soft delete product")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ApiResponse.builder().success(true).message("Product deleted successfully").data(null).build();
    }

    @GetMapping("/filter")
    public ApiResponse<PaginatedResponseDto<ProductResponseDto>> filterProducts(@RequestParam(required = false) String productName, @RequestParam(required = false) String productType, @RequestParam(required = false) BigDecimal minRate,

                                                                                @RequestParam(required = false) BigDecimal maxRate,

                                                                                @RequestParam(defaultValue = "0") int page,

                                                                                @RequestParam(defaultValue = "5") int size) {

        PaginatedResponseDto<ProductResponseDto> response = productService.filterProducts(productName, productType, minRate, maxRate, page, size);

        return ApiResponse.<PaginatedResponseDto<ProductResponseDto>>builder().success(true).message("Products filtered successfully").data(response).build();
    }

}
