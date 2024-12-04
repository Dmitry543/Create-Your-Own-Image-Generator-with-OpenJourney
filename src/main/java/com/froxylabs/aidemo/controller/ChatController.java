package com.froxylabs.aidemo.controller;

import com.froxylabs.aidemo.client.HuggingFaceModelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai")
public class ChatController {

    @Value("${access.token}")
    private String accessToken;

    private HuggingFaceModelClient client;

    @Autowired
    public ChatController() {
        this.client = HuggingFaceModelClient.builder()
                .modelName("prompthero/openjourney-v4")
                .accessToken(accessToken)
                .maxLength(100)
                .maxRetries(5)
                .retryDelay(1000)
                .xWaitForModel(true)
                .numInferenceSteps(10)
                .build();
    }

}
