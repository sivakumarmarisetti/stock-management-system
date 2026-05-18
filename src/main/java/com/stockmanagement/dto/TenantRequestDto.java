package com.stockmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantRequestDto {

    @NotBlank(message = "Tenant code is required")
    private String tenantCode;

    @NotBlank(message = "Tenant name is required")
    private String tenantName;
}