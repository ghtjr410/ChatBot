package com.chatbot.api.auth.application.dto;

import java.util.List;

public record UserInfoData(
        long id,
        String email,
        String name,
        List<String> roles
) {
    public static UserInfoData from(long id, String email, String name, List<String> roles) {
        return new UserInfoData(id, email, name, roles);
    }
}
