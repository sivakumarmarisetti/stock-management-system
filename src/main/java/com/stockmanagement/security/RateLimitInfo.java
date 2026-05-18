package com.stockmanagement.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimitInfo {

    private int requestCount;

    private long windowStartTime;
}