package com.useclient.zenvironment.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.useclient.zenvironment.configuration.ChatAssistantProperties;
import com.useclient.zenvironment.model.dto.chat.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

@Service
public class AssistantService {

    private static final String MODEL = "gpt-3.5-turbo";
    private static final Integer MAX_TOKENS = 256;

    private final OpenAiService assistant;

    private final Map<String, List<ChatMessage>> conversationHistory;

    public AssistantService(ChatAssistantProperties props) {
        this.assistant = new OpenAiService(props.getApiKey());
        this.conversationHistory = new HashMap<>();
    }

    public Message getAssistance(Message message) {
        var conversationId = message.getConversationId();
        List<ChatMessage> messageHistory;
        if (conversationId != null && conversationHistory.containsKey(conversationId)) {
            messageHistory = conversationHistory.get(conversationId);
            messageHistory.add(new ChatMessage(ChatMessageRole.USER.value(), message.getMessage()));
        } else {
            conversationId = UUID.randomUUID().toString();
            messageHistory = generateFirstAiChatMessage(message.getMessage());
        }
        var messages = new ArrayList<>(messageHistory);
        var chatCompletionRequest = ChatCompletionRequest.builder()
                .model(MODEL)
                .messages(messages)
                .maxTokens(MAX_TOKENS)
                .build();
        var response = assistant.createChatCompletion(chatCompletionRequest);
        var choices = response.getChoices();
        if (CollectionUtils.isEmpty(choices)) {
            var apology = "Apologies, but I can't give you an answer at the moment. Why don't you come back later when the weather's more favorable?";
            messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), apology));
            conversationHistory.put(conversationId, messages);
            return new Message(apology, conversationId);
        }
        var responseMessage = choices.get(0).getMessage();
        messages.add(responseMessage);
        conversationHistory.put(conversationId, messages);
        return new Message(responseMessage.getContent(), conversationId);
    }

    private List<ChatMessage> generateFirstAiChatMessage(String message) {
        return new ArrayList<>(Stream.of("answer in one sentence",
                        "answer like an old american farmer",
                        "use imperial and metric units",
                        message
                )
                .map(messageContent -> new ChatMessage(ChatMessageRole.USER.value(), messageContent))
                .toList());
    }

}
