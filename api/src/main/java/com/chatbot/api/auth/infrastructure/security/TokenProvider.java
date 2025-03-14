package com.chatbot.api.auth.infrastructure.security;

import com.chatbot.api.auth.application.dto.TokenInfoData;
import com.chatbot.api.auth.application.util.JwtClaims;
import com.chatbot.api.auth.application.util.TokenType;
import com.chatbot.api.auth.domain.User;
import com.chatbot.api.common.infrastructure.config.JwtProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public TokenInfoData generateToken(User user, List<String> roles) {
        String accessToken = generateAccessToken(user, roles);
        LocalDateTime accessTokenExpiresDate = getAccessTokenExpiresDate();
        return TokenInfoData.from(accessToken, "Bearer", accessTokenExpiresDate);
    }

    public String generateAccessToken(User user, List<String> roles) {
        return buildToken(user, roles, TokenType.ACCESS_TOKEN, jwtProperties.getAccessTokenExpiration());
    }

    private String buildToken(User user, List<String> roles, TokenType tokenType, long expirationTime) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim(JwtClaims.EMAIL.getKey(), user.getEmail())
                .claim(JwtClaims.NAME.getKey(), user.getName())
                .claim(JwtClaims.TOKEN_TYPE.getKey(), tokenType.getKey())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(calculateExpirationDate(expirationTime)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256);

        if (roles != null) {
            jwtBuilder.claim(JwtClaims.ROLES.getKey(), roles);
        }

        return jwtBuilder.compact();
    }

    private LocalDateTime getAccessTokenExpiresDate() {
        return calculateExpirationDate(jwtProperties.getAccessTokenExpiration());
    }

    private LocalDateTime calculateExpirationDate(long expirationMillis) {
        return  LocalDateTime.now()
                .plus(Duration.ofMillis(expirationMillis))
                .withNano(0);
    }

    private Key getSignInKey() {
        byte[] keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
