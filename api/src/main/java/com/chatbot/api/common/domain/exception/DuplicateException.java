package com.chatbot.api.common.domain.exception;

import com.chatbot.api.common.util.BaseMessage;

public class DuplicateException extends RuntimeException{

    public DuplicateException() {
    }

    public DuplicateException(BaseMessage message) {
        super(message.getMessage());
    }

    public DuplicateException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }
}
