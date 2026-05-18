package com.stockmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {

    private boolean success;

    private String errorCode;

    private String message;

    private LocalDateTime timestamp;
}