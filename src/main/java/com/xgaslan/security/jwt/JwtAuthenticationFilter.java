package com.xgaslan.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgaslan.exceptions.ErrorCodes;
import com.xgaslan.result.ServiceResult;
import com.xgaslan.security.PublicEndpointRegistry;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PublicEndpointRegistry publicEndpointRegistry;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher antPathMatcher;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, PublicEndpointRegistry publicEndpointRegistry, ObjectMapper objectMapper, AntPathMatcher antPathMatcher) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.publicEndpointRegistry = publicEndpointRegistry;
        this.objectMapper = objectMapper;
        this.antPathMatcher = antPathMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = extractJwtFromRequest(request);
        String email = null;

        if (jwtToken != null) {
            try {
                email = jwtService.getEmailFromToken(jwtToken); // Artık email!
            } catch (ExpiredJwtException ex) {
                log.warn("[{}] JwtToken expired: {}", request.getRemoteAddr(), ex.getMessage());
                sendServiceResultError(response, HttpServletResponse.SC_UNAUTHORIZED,
                        ErrorCodes.System.UNAUTHORIZED_ERROR,
                        "JWT token expired",
                        ex.getMessage());
                return;
            } catch (Exception ex) {
                log.warn("Invalid JWT token for request to {}: {}", path, ex.getMessage());
                sendServiceResultError(response, HttpServletResponse.SC_UNAUTHORIZED,
                        ErrorCodes.System.UNAUTHORIZED_ERROR,
                        "Invalid JWT token",
                        ex.getMessage());
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email); // email parametresi
            if (!jwtService.validateToken(jwtToken, userDetails)) {
                log.warn("JWT token failed validation for user: {}", email);
                sendServiceResultError(response, HttpServletResponse.SC_UNAUTHORIZED,
                        ErrorCodes.System.UNAUTHORIZED_ERROR,
                        "JWT token invalid or expired",
                        "Failed validation");
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("Authenticated user '{}', set security context.", email);
        }

        filterChain.doFilter(request, response);
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank()) {
            String[] parts = authHeader.split("\\s+");
            if (parts.length == 2 && "Bearer".equalsIgnoreCase(parts[0])) {
                return parts[1].trim();
            } else if (parts.length == 1) {
                return parts[0].trim();
            }
        }
        return null;
    }

    private boolean isPublicEndpoint(String path) {
        return publicEndpointRegistry.getPublicEndpoints().stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }

    private void sendServiceResultError(HttpServletResponse response, int status, String code, String message, String detail) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        var errorResult = ServiceResult.failure(code, message, detail, status);
        objectMapper.writeValue(response.getWriter(), errorResult);
    }
}
