package com.chatbot.api.auth.domain;

import com.chatbot.api.common.util.BaseMessage;
import lombok.Getter;

@Getter
public enum UserErrorMessage implements BaseMessage {
    EMAIL_DUPLICATE("해당 이메일이 이미 존재합니다."),
    USER_NOT_FOUND("회원 정보를 찾을 수 없습니다.");

    private final String message;

    UserErrorMessage(String message) {
        this.message = message;
    }
}
