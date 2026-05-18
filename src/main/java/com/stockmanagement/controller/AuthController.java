package com.stockmanagement.controller;

import com.stockmanagement.dto.ApiResponse;
import com.stockmanagement.dto.LoginRequestDto;
import com.stockmanagement.dto.LoginResponseDto;
import com.stockmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication", description = "Authentication and authorization APIs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login User", description = "Authenticate user and generate JWT token")
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request) {

        LoginResponseDto response = authService.login(requestDto, request);

        return ApiResponse.<LoginResponseDto>builder().success(true).message("Login successful").data(response).build();
    }
}