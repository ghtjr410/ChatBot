package com.chatbot.api.auth.infrastructure.security;

import com.chatbot.api.auth.application.dto.UserInfoData;
import com.chatbot.api.auth.application.util.JwtClaims;
import com.chatbot.api.common.infrastructure.config.JwtProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenParser {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public UserInfoData extractUserInfo(String token) {
        Claims claims = parseClaims(token);
        validateTokenExpiration(claims);

        return UserInfoData.from(
                extractSubject(claims),
                extractEmail(claims),
                extractName(claims),
                extractRoles(claims)
        );
    }

    private Long extractSubject(Claims claims) {
        return Long.valueOf(claims.getSubject().trim());
    }
    private String extractEmail(Claims claims) {
        return claims.get(JwtClaims.EMAIL.getKey(), String.class);
    }
    private String extractName(Claims claims) {
        return claims.get(JwtClaims.NAME.getKey(), String.class);
    }

    private List<String> extractRoles(Claims claims) {
        return objectMapper.convertValue(
                claims.get(JwtClaims.ROLES.getKey()), new TypeReference<List<String>>() {});
    }

    private void validateTokenExpiration(Claims claims) {
        LocalDateTime expiration = claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (expiration.isBefore(LocalDateTime.now())) {
            throw new SecurityException();
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
