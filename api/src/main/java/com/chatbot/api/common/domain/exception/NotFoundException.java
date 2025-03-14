package com.chatbot.api.common.domain.exception;

import com.chatbot.api.common.util.BaseMessage;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(BaseMessage message) {
        super(message.getMessage());
    }

    public NotFoundException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
