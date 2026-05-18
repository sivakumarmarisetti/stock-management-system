package com.stockmanagement.security;

import com.stockmanagement.service.AuditLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuditLoggingFilter extends OncePerRequestFilter {

    private final AuditLogService auditLogService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        filterChain.doFilter(request, response);

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = "ANONYMOUS";

        if (authentication != null &&
                authentication.isAuthenticated()) {

            username = authentication.getName();
        }

        auditLogService.saveLog(
                username,
                "API_ACCESS",
                request.getRequestURI(),
                request.getMethod()
        );
    }
}