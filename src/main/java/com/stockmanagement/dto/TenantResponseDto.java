package com.stockmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantResponseDto {

    private Long id;

    private String tenantCode;

    private String tenantName;

    private Boolean active;
}