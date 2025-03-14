package com.chatbot.api.chat.application.dto;

import java.util.List;

public record ChatGPTRequest(
        String model,
        List<ChatGPTMessage> messages
) {
    public static ChatGPTRequest from(String model, List<ChatGPTMessage> messages) {
        return new ChatGPTRequest(
                model,
                messages
        );
    }
}
