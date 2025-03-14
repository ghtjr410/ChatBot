package com.chatbot.api.auth.infrastructure.security;

import com.chatbot.api.auth.application.dto.UserInfoData;
import com.chatbot.api.auth.application.util.UserPrincipal;
import com.chatbot.api.auth.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityContextManager {

    public void setAuthentication(UserInfoData data) {
        List<GrantedAuthority> authorities = data.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User user = User.builder()
                .id(data.id())
                .email(data.email())
                .name(data.name())
                .build();

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                UserPrincipal.of(user), null, authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
