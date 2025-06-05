package com.xgaslan.security.jwt;

import com.xgaslan.data.configs.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    protected Map<String, Object> buildClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // claims.put("roles", userDetails.getAuthorities());
        return claims;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = buildClaims(userDetails);
        // subject olarak email (userDetails.getUsername() email zaten)
        return buildToken(claims, userDetails.getUsername());
    }

    private String buildToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject) // subject=email
                .issuer(jwtConfig.getIssuer())
                .audience().add(jwtConfig.getAudience()).and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTimeMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        var secret = jwtConfig.getSecret();
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret is not set!");
        }
        var keyBytes = Decoders.BASE64.decode(secret);
        if (keyBytes.length < 32) {
            throw new IllegalStateException("JWT secret key must be at least 256 bits (32 base64 chars)!");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), "JWT token has expired", ex);
        } catch (SecurityException | JwtException ex) {
            throw new JwtException("Invalid JWT token", ex);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String email = extractAllClaims(token).getSubject();
            return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    public String getEmailFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }
}
