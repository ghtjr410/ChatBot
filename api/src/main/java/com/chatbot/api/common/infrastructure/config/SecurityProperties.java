package com.chatbot.api.common.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> permitAllUrls;
    private List<String> adminOnlyUrls;

    public String[] getPermitAllUrlsAsArray() {
        return convertListToArray(permitAllUrls);
    }

    public String[] getAdminOnlyUrlsAsArray() {
        return convertListToArray(adminOnlyUrls);
    }

    public boolean isPermitted(String requestURI) {
        return Optional.ofNullable(permitAllUrls)
                .orElse(List.of())
                .stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, requestURI));
    }

    private String[] convertListToArray(List<String> urls) {
        return Optional.ofNullable(urls)
                .orElse(List.of())
                .toArray(new String[0]);
    }
}
