package com.stockmanagement.mapper;

import com.stockmanagement.dto.TenantResponseDto;
import com.stockmanagement.entity.Tenant;

public class TenantMapper {

    public static TenantResponseDto
    mapToTenantResponseDto(Tenant tenant) {

        return TenantResponseDto.builder()
                .id(tenant.getId())
                .tenantCode(tenant.getTenantCode())
                .tenantName(tenant.getTenantName())
                .active(tenant.getActive())
                .build();
    }
}