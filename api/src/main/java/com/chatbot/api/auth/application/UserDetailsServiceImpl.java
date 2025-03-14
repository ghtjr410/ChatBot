package com.chatbot.api.auth.application;

import com.chatbot.api.auth.application.util.UserPrincipal;
import com.chatbot.api.auth.domain.User;
import com.chatbot.api.auth.infrastructure.repository.UserJpaRepository;
import com.chatbot.api.common.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chatbot.api.auth.domain.UserErrorMessage.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return UserPrincipal.of(user);
    }
}
