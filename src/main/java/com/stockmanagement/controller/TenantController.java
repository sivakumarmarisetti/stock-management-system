package com.stockmanagement.controller;

import com.stockmanagement.dto.*;
import com.stockmanagement.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<TenantResponseDto>
    createTenant(
            @Valid @RequestBody
            TenantRequestDto requestDto) {

        TenantResponseDto tenant =
                tenantService.createTenant(requestDto);

        return ApiResponse
                .<TenantResponseDto>builder()
                .success(true)
                .message("Tenant created successfully")
                .data(tenant)
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<List<TenantResponseDto>>
    getAllTenants() {

        List<TenantResponseDto> tenants =
                tenantService.getAllTenants();

        return ApiResponse
                .<List<TenantResponseDto>>builder()
                .success(true)
                .message("Tenants fetched successfully")
                .data(tenants)
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<TenantResponseDto>
    getTenantById(@PathVariable Long id) {

        TenantResponseDto tenant =
                tenantService.getTenantById(id);

        return ApiResponse
                .<TenantResponseDto>builder()
                .success(true)
                .message("Tenant fetched successfully")
                .data(tenant)
                .build();
    }
}