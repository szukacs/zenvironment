package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.model.dto.chat.Message;
import com.useclient.zenvironment.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assistant")
@RequiredArgsConstructor
public class AssistantController {
    private final AssistantService assistantService;

    @Transactional(readOnly = true)
    @PostMapping("/chat")
    public ResponseEntity<Message> askForAssistance(@RequestBody Message chatRequest) {
        try {
            var response = assistantService.getAssistance(chatRequest.getMessage());
            return ResponseEntity.ok(new Message(response));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(readOnly = true)
    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getQuickQuestions() {
        return ResponseEntity.ok(assistantService.getGardenBasedAssistanceQuestions());
    }

}
