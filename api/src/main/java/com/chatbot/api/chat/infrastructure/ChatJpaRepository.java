package com.chatbot.api.chat.infrastructure;

import com.chatbot.api.auth.domain.User;
import com.chatbot.api.chat.domain.Chat;
import com.chatbot.api.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByChatRoomOrderByCreatedDateAsc(ChatRoom chatRoom);
}
