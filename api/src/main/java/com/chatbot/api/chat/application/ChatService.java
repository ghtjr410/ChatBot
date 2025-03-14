package com.chatbot.api.chat.application;

import com.chatbot.api.auth.domain.User;
import com.chatbot.api.auth.infrastructure.repository.UserJpaRepository;
import com.chatbot.api.chat.api.dto.request.ChatRequest;
import com.chatbot.api.chat.api.dto.response.ChatResponse;
import com.chatbot.api.chat.domain.Chat;
import com.chatbot.api.chat.domain.ChatRoom;
import com.chatbot.api.chat.infrastructure.ChatGPTClient;
import com.chatbot.api.chat.infrastructure.ChatJpaRepository;
import com.chatbot.api.chat.infrastructure.ChatRoomJpaRepository;
import com.chatbot.api.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatJpaRepository chatJpaRepository;
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ChatGPTClient chatGPTClient;
    private final UserJpaRepository userJpaRepository;
    private final ClockHolder clockHolder;

    public ChatResponse createChat(Long userId, ChatRequest request) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChatRoom chatRoom = getOrCreateChatRoom(user);

        // OpenAI API 호출
        String answer = chatGPTClient.chat(request.message());

        // DB에 저장
        Chat chat = Chat.create(chatRoom, request.message(), answer, clockHolder.now());
        chatJpaRepository.save(chat);

        return new ChatResponse(chat.getId(), request.message(), answer,
                chat.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public List<ChatResponse> getUserChats(Long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChatRoom chatRoom = chatRoomJpaRepository.findTopByUserOrderByCreatedDateDesc(user)
                .orElseThrow(() -> new IllegalArgumentException("사용자의 대화방이 존재하지 않습니다."));

        return chatJpaRepository.findByChatRoomOrderByCreatedDateAsc(chatRoom)
                .stream()
                .map(chat -> new ChatResponse(
                        chat.getId(), chat.getQuestion(), chat.getAnswer(),
                        chat.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .toList();
    }

    private ChatRoom getOrCreateChatRoom(User user) {
        return chatRoomJpaRepository.findTopByUserOrderByCreatedDateDesc(user)
                .filter(chatRoom -> chatRoom.getCreatedDate().isAfter(clockHolder.now().minusMinutes(30)))
                .orElseGet(() -> {
                    ChatRoom newChatRoom = ChatRoom.create(user, clockHolder.now());
                    chatRoomJpaRepository.save(newChatRoom);
                    return newChatRoom;
                });
    }
}
