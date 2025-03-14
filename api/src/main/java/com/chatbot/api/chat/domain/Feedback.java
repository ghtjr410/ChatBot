package com.chatbot.api.chat.domain;

import com.chatbot.api.auth.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "positive", nullable = false)
    private boolean isPositive;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FeedbackStatus status; // PENDING, RESOLVED

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public static Feedback create(User user, Chat chat, boolean isPositive, FeedbackStatus status, LocalDateTime now) {
        return Feedback.builder()
                .user(user)
                .chat(chat)
                .isPositive(isPositive)
                .status(status)
                .createdDate(now)
                .build();
    }
}
