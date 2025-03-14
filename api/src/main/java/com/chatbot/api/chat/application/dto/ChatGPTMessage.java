package com.chatbot.api.chat.application.dto;

public record ChatGPTMessage(
        String role,
        String content
) {
    public static ChatGPTMessage system(String content) {
        return new ChatGPTMessage("system", content);
    }
    public static ChatGPTMessage user(String content) {
        return new ChatGPTMessage("user", content);
    }
}
