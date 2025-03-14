package com.chatbot.api.auth.application.util;

import lombok.Getter;

@Getter
public enum JwtClaims {
    EMAIL("email"),
    NAME("name"),
    ROLES("roles"),
    TOKEN_TYPE("type");

    private final String key;

    JwtClaims(String key) {
        this.key = key;
    }
}
