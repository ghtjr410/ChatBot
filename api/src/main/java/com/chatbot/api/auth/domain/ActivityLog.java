package com.chatbot.api.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "activity_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_log_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActivityLogType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public static ActivityLog create(ActivityLogType type, User user, LocalDateTime now) {
        return ActivityLog.builder()
                .type(type)
                .user(user)
                .createdDate(now)
                .build();
    }
}
