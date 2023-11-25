package com.useclient.zenvironment.controller;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.useclient.zenvironment.configuration.ChatAssistantProperties;
import com.useclient.zenvironment.model.dto.chat.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/assistant")
public class AssistantController {
    private static final String MODEL = "gpt-3.5-turbo";
    private static final Integer MAX_TOKENS = 256;

    private final OpenAiService assistant;

    public AssistantController(ChatAssistantProperties props) {
        assistant = new OpenAiService(props.getApiKey());
    }

    @PostMapping("/chat")
    public ResponseEntity<Message> askForAssistance(@RequestBody Message chatRequest) {
        var chatCompletionRequest = ChatCompletionRequest.builder()
                .model(MODEL)
                .messages(convertChatRequest(chatRequest))
                .maxTokens(MAX_TOKENS)
                .build();
        var response = assistant.createChatCompletion(chatCompletionRequest);
        var choices = response.getChoices();
        if (CollectionUtils.isEmpty(choices)) {
            return ResponseEntity.notFound().build();
        }
        var message = choices.get(0).getMessage();
        return ResponseEntity.ok(new Message(message.getContent()));
    }

    private List<ChatMessage> convertChatRequest(Message message) {
        return Stream.of("answer in one sentence",
                        "answer like an old american farmer",
                        "use imperial and metric units",
                        message.getMessage()
                )
                .map(messageContent -> new ChatMessage(ChatMessageRole.USER.value(), messageContent))
                .toList();
    }
}
