package com.chatbot.api.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;


    public static User create(String email, String password, String name, UserRoleType role, LocalDateTime now) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .createdDate(now)
                .build();
    }

}

