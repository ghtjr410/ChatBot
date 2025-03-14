package com.chatbot.api.auth.application.dto;

import java.time.LocalDateTime;

public record TokenInfoData(
        String accessToken,
        String tokenType,
        LocalDateTime expiresDate
) {
    public static TokenInfoData from(String accessToken, String tokenType, LocalDateTime expiresDate) {
        return new TokenInfoData(accessToken, tokenType, expiresDate);
    }
}
