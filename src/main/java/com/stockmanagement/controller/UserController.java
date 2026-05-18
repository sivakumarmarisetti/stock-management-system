package com.stockmanagement.controller;

import com.stockmanagement.dto.ApiResponse;
import com.stockmanagement.dto.UserRegistrationRequestDto;
import com.stockmanagement.dto.UserResponseDto;
import com.stockmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register User", description = "Register a new user")
    @PostMapping("/register")
    public ApiResponse<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationRequestDto requestDto) {
        UserResponseDto user = userService.registerUser(requestDto);

        return ApiResponse.<UserResponseDto>builder().success(true).message("User registered successfully").data(user).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUser();

        return ApiResponse.<List<UserResponseDto>>builder().success(true).message("Users fetched successfully").data(users).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getUserById(@PathVariable Long id) {

        UserResponseDto user = userService.getUserById(id);

        return ApiResponse.<UserResponseDto>builder().success(true).message("user fetched successfully").data(user).build();
    }

}
