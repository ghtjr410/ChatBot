package com.chatbot.api.chat.api.dto.response;

public record ChatResponse(
        Long id,
        String question,
        String answer,
        String createdDate
) {
}
