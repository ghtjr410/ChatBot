package com.chatbot.api.auth.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String name
) {
}
