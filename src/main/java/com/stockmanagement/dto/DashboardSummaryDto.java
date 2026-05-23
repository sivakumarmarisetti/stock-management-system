package com.stockmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryDto {
    private long totalProducts;
    private long totalUsers;
    private long totalStockAdds;
    private long totalStockReduces;
    private long lowStockProducts;  // products with quantity < 10
}