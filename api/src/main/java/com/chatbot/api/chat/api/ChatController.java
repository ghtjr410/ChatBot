package com.chatbot.api.chat.api;

import com.chatbot.api.auth.application.util.UserPrincipal;
import com.chatbot.api.chat.api.dto.request.ChatRequest;
import com.chatbot.api.chat.api.dto.response.ChatResponse;
import com.chatbot.api.chat.application.ChatService;
import com.chatbot.api.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "새로운 대화 생성", description = "사용자의 질문을 받아 챗봇과 대화합니다.")
    @PostMapping
    public ApiResponse<ChatResponse> createChat(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ChatRequest request
    ) {
        Long userId = userPrincipal.getUser().getId();
        return ApiResponse.success(chatService.createChat(userId, request));
    }

    @Operation(summary = "사용자의 대화 기록 조회", description = "현재 사용자의 전체 대화 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<ChatResponse>> getUserChats(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Long userId = userPrincipal.getUser().getId();
        return ApiResponse.success(chatService.getUserChats(userId));
    }
}
