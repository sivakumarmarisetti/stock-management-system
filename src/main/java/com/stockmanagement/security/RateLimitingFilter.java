package com.stockmanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmanagement.dto.ErrorResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 5;

    private static final long WINDOW_DURATION =
            60 * 1000;

    private final Map<String, RateLimitInfo>
            requestCounts = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String path = request.getRequestURI();
        String username = "ANONYMOUS";

        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/webjars") ||
                path.startsWith("/swagger-resources")) {
            filterChain.doFilter(request, response);
            return;
        }


        if (authentication != null &&
                authentication.isAuthenticated()) {

            username = authentication.getName();
        }

        long currentTime = System.currentTimeMillis();

        RateLimitInfo rateLimitInfo =
                requestCounts.get(username);

        if (rateLimitInfo == null) {

            rateLimitInfo = new RateLimitInfo(
                    1,
                    currentTime
            );

            requestCounts.put(username, rateLimitInfo);

        } else {

            long elapsedTime =
                    currentTime -
                            rateLimitInfo.getWindowStartTime();

            if (elapsedTime > WINDOW_DURATION) {

                rateLimitInfo.setRequestCount(1);

                rateLimitInfo.setWindowStartTime(
                        currentTime
                );

            } else {

                rateLimitInfo.setRequestCount(
                        rateLimitInfo.getRequestCount() + 1
                );

                if (rateLimitInfo.getRequestCount()
                        > MAX_REQUESTS) {

                    ErrorResponseDto errorResponse =
                            ErrorResponseDto.builder()
                                    .success(false)
                                    .errorCode("RATE_LIMIT_EXCEEDED")
                                    .message("Too many requests. Please try again later.")
                                    .timestamp(LocalDateTime.now())
                                    .build();

                    response.setStatus(
                            HttpStatus.TOO_MANY_REQUESTS.value()
                    );

                    response.setContentType(
                            "application/json"
                    );

                    response.getWriter().write(
                            objectMapper.writeValueAsString(
                                    errorResponse
                            )
                    );

                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}