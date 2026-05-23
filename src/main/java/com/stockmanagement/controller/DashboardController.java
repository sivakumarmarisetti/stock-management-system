package com.stockmanagement.controller;

import com.stockmanagement.dto.ApiResponse;
import com.stockmanagement.dto.DashboardSummaryDto;
import com.stockmanagement.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<DashboardSummaryDto> getSummary() {
        return ApiResponse.<DashboardSummaryDto>builder()
                .success(true)
                .message("Dashboard summary fetched successfully")
                .data(dashboardService.getSummary())
                .build();
    }
}