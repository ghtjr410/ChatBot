package com.chatbot.api.auth.application;

import com.chatbot.api.auth.api.dto.request.LoginRequest;
import com.chatbot.api.auth.api.dto.request.SignupRequest;
import com.chatbot.api.auth.application.dto.TokenInfoData;
import com.chatbot.api.auth.application.util.UserPrincipal;
import com.chatbot.api.auth.domain.User;
import com.chatbot.api.auth.domain.UserErrorMessage;
import com.chatbot.api.auth.domain.UserRoleType;
import com.chatbot.api.auth.infrastructure.repository.UserJpaRepository;
import com.chatbot.api.auth.infrastructure.security.SecurityExtractor;
import com.chatbot.api.auth.infrastructure.security.TokenProvider;
import com.chatbot.api.common.application.port.ClockHolder;
import com.chatbot.api.common.domain.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SecurityExtractor securityExtractor;
    private final TokenProvider tokenProvider;
    private final UserJpaRepository userJpaRepository;
    private final ClockHolder clockHolder;

    public void signup(SignupRequest request, UserRoleType type) {
        if (userJpaRepository.existsByEmail(request.email())) {
            throw new DuplicateException(UserErrorMessage.EMAIL_DUPLICATE);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.create(request.email(), encodedPassword, request.name(), type, clockHolder.now());
        userJpaRepository.save(user);
    }

    public TokenInfoData login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password())
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        List<String> roles = securityExtractor.extractRoles(principal.getAuthorities());

        return tokenProvider.generateToken(user, roles);
    }
}
