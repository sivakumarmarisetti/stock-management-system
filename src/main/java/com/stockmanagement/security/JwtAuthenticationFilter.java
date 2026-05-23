package com.stockmanagement.security;

import com.stockmanagement.service.CustomUserDetailsService;
import com.stockmanagement.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // No token present — skip JWT processing entirely
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = null;
        Long tenantId = null;

        try {
            // Extract claims — throws ExpiredJwtException if token is expired
            username = jwtUtil.extractUsername(jwt);
            tenantId = jwtUtil.extractTenantId(jwt);

        } catch (ExpiredJwtException e) {
            // Token is expired — clear security context and continue
            // Spring Security will return 401 since no authentication is set
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;

        } catch (MalformedJwtException | SignatureException e) {
            // Token is tampered or invalid — treat as unauthenticated
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;

        } catch (Exception e) {
            // Any other JWT parsing error — clear and continue
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        // Set tenant context for multi-tenant query filtering
        if (tenantId != null) {
            TenantContext.setTenantId(tenantId);
        }

        try {
            // Only set authentication if not already authenticated
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.isTokenValid(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } finally {
            // Always clear tenant context after request completes
            // Prevents tenant data leaking into the next request on the same thread
            TenantContext.clear();
        }
    }
}