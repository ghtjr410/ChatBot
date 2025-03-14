package com.chatbot.api.chat.infrastructure;

import com.chatbot.api.auth.domain.User;
import com.chatbot.api.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findTopByUserOrderByCreatedDateDesc(User user);
}
