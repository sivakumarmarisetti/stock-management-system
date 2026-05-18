package com.stockmanagement.service;

import com.stockmanagement.dto.LoginRequestDto;
import com.stockmanagement.dto.LoginResponseDto;
import com.stockmanagement.entity.User;
import com.stockmanagement.exception.ResourceNotFoundException;
import com.stockmanagement.repository.UserRepository;
import com.stockmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.stockmanagement.entity.LoginStatus;
import jakarta.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LoginTrackingService loginTrackingService;

    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletRequest request) {

        User user = userRepository
                .findByUsername(requestDto.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                user.getPassword())) {
            loginTrackingService.trackLogin(
                    requestDto.getUsername(),
                    LoginStatus.FAILED,
                    request
            );

            throw new RuntimeException(
                    "Invalid username or password");
        }

        String token =
                jwtUtil.generateToken(user.getUsername(),user.getTenant().getId());

        loginTrackingService.trackLogin(
                user.getUsername(),
                LoginStatus.SUCCESS,
                request
        );

        return LoginResponseDto.builder()
                .token(token)
                .type("Bearer")
                .build();
    }
}