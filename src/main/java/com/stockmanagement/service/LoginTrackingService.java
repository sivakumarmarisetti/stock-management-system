package com.stockmanagement.service;

import com.stockmanagement.entity.LoginStatus;
import com.stockmanagement.entity.LoginTracking;
import com.stockmanagement.repository.LoginTrackingRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginTrackingService {

    private final LoginTrackingRepository
            loginTrackingRepository;

    public void trackLogin(
            String username,
            LoginStatus status,
            HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        String userAgent =
                request.getHeader("User-Agent");

        LoginTracking loginTracking =
                LoginTracking.builder()
                        .username(username)
                        .ipAddress(ipAddress)
                        .userAgent(userAgent)
                        .loginStatus(status)
                        .loginTime(LocalDateTime.now())
                        .build();

        loginTrackingRepository.save(loginTracking);
    }
}