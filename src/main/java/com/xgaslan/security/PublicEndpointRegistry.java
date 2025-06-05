package com.xgaslan.security;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicEndpointRegistry {
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/refresh-token",
            "/auth/logout",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/verify-email",
            "/auth/verify-email-resend",
            "/auth/verify-email/{token}",
            "/health/alive",
            "/swagger/**"
            // yeni public endpoint'leri buraya ekle!
    );

    public List<String> getPublicEndpoints() {
        return PUBLIC_ENDPOINTS;
    }
}
