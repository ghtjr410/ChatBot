package com.chatbot.api.common.application.port;

import java.time.LocalDateTime;

public interface ClockHolder {
    LocalDateTime now();
}
