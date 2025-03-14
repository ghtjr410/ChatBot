package com.chatbot.api.common.infrastructure.config;

import com.chatbot.api.auth.application.dto.UserInfoData;
import com.chatbot.api.auth.infrastructure.security.SecurityContextManager;
import com.chatbot.api.auth.infrastructure.security.SecurityExtractor;
import com.chatbot.api.auth.infrastructure.security.TokenParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityProperties securityProperties;
    private final SecurityExtractor securityExtractor;
    private final TokenParser tokenParser;
    private final SecurityContextManager securityContextManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getServletPath();
        log.info(requestURI);
        if (securityProperties.isPermitted(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("1");
        String authHeader = request.getHeader("Authorization");
        String extractedToken = securityExtractor.extractToken(authHeader);
        log.info("2");
        UserInfoData userInfoData = tokenParser.extractUserInfo(extractedToken);
        log.info("3");

        securityContextManager.setAuthentication(userInfoData);
        log.info("4");
        filterChain.doFilter(request, response);
    }
}
