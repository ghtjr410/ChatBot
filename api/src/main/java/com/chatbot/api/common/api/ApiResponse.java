package com.chatbot.api.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record ApiResponse<T>(
        String message,
        @JsonInclude(NON_NULL)
        T data
) {
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ResponseMessage.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseMessage.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null);
    }
}
