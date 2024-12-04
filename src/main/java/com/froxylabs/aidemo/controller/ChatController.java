package com.froxylabs.aidemo.controller;

import com.froxylabs.aidemo.client.HuggingFaceModelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class ChatController {
    private final String ACCESS_TOKEN = "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";

    private HuggingFaceModelClient client;

    @Autowired
    public ChatController() {
        this.client = HuggingFaceModelClient.builder()
                .modelName("google-t5/t5-small")
                .accessToken(ACCESS_TOKEN)
                .maxLength(100)
                .maxRetries(5)
                .retryDelay(1000)
                .build();
    }

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "I love coffee") String message) throws IOException {
        return Map.of("generation", client.call(message));
    }
}
