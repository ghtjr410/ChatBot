package com.chatbot.api.common.api;

import com.chatbot.api.common.util.BaseMessage;
import lombok.Getter;

@Getter
public enum ResponseMessage implements BaseMessage {
    SUCCESS("Success"),
    ERROR("Error");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
}
