package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.model.dto.chat.Message;
import com.useclient.zenvironment.service.AssistantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assistant")
@RequiredArgsConstructor
public class AssistantController {
    private final AssistantService assistantService;

    @Operation(summary = "Old Sam answers all of your questions", description = "Old Sam answers all of your questions")
    @Transactional(readOnly = true)
    @PostMapping("/chat")
    public ResponseEntity<Message> askForAssistance(@RequestBody Message chatRequest) {
        try {
            var response = assistantService.getAssistance(chatRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
