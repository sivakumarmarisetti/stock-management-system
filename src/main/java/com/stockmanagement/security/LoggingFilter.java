package com.stockmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID =
            "X-Correlation-ID";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId =
                request.getHeader(CORRELATION_ID);

        if (correlationId == null ||
                correlationId.isBlank()) {

            correlationId =
                    UUID.randomUUID().toString();
        }

        MDC.put(CORRELATION_ID, correlationId);

        long startTime =
                System.currentTimeMillis();

        try {

            log.info(
                    "Incoming Request -> Method: {}, URI: {}, CorrelationId: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    correlationId
            );

            String username = "ANONYMOUS";

            if (request.getUserPrincipal() != null) {
                username = request.getUserPrincipal().getName();
            }
            Long tenantId = TenantContext.getTenantId();
            log.info("User: {}, TenantId: {}", username, tenantId);
            filterChain.doFilter(request, response);

        } finally {

            long duration =
                    System.currentTimeMillis() - startTime;

            log.info(
                    "Outgoing Response -> Status: {}, Duration: {} ms, CorrelationId: {}",
                    response.getStatus(),
                    duration,
                    correlationId
            );

            MDC.clear();
        }
    }
}