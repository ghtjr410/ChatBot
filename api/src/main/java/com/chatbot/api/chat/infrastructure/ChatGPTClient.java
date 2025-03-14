package com.chatbot.api.chat.infrastructure;

import com.chatbot.api.chat.application.dto.ChatGPTMessage;
import com.chatbot.api.chat.application.dto.ChatGPTRequest;
import com.chatbot.api.common.infrastructure.config.OpenAIProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatGPTClient {

    private final RestClient restClient;
    private final OpenAIProperties openAIProperties;

    public String chat(String prompt) {
        List<ChatGPTMessage> messages = new ArrayList<>();

        messages.add(ChatGPTMessage.system("당신은 친절한 고민 상담사 입니다."));
        messages.add(ChatGPTMessage.user(prompt));

        ChatGPTRequest request = ChatGPTRequest.from(openAIProperties.getModel(), messages);

        return restClient.post()
                .header("Authorization", "Bearer " + openAIProperties.getKey())
                .header("Content-Type", openAIProperties.getMediaType())
                .body(request)
                .retrieve()
                .body(String.class);
    }
}
