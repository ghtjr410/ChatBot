package com.chatbot.api.common.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "openai.api")
public class OpenAIProperties {
    private String model;
    private String key;
    private String mediaType;
}
