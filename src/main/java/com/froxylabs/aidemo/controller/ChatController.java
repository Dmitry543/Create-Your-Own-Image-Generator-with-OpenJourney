package com.froxylabs.aidemo.controller;

import com.froxylabs.aidemo.client.HuggingFaceModelClient;
import com.froxylabs.aidemo.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

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

    @GetMapping("/generate")
    public String showGenerationPage(Model model) {
        model.addAttribute("generation", new Generation());
        model.addAttribute("prompt", "");

        return "index";
    }

}
