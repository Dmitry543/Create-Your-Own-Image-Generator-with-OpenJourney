package com.froxylabs.aidemo.controller;

import com.froxylabs.aidemo.client.HuggingFaceModelClient;
import com.froxylabs.aidemo.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

        String imageBase64="";
        try {
            File file = new File("src/main/resources/static/images/defaultImage.jpg");
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            imageBase64 = Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("imageBase64",imageBase64);

        return "index";
    }

    @PostMapping(value = "/generate")
    public String generateImage(@ModelAttribute Generation generation, Model model) throws IOException {
        model.addAttribute("prompt",generation.getPrompt());
        model.addAttribute("negativePrompt",generation.getNegativePrompt());

        //Get the generation result as byte array from AI model
        byte[] image = client.call(generation.getPrompt(),generation.getNegativePrompt());

        //Saving the picture

        //Create the object of type ByteArrayInputStream and read the byte array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
        //Read the image to BufferedImage
        BufferedImage newImage = ImageIO.read(inputStream);
        //Create a new file and write an image to file
        ImageIO.write(newImage,"jpg",new File("src/main/resources/static/images/outputImage.jpg"));

        //Encoding to Base64 and sending to thymeleaf view
        String imageBase64 = Base64.getEncoder().encodeToString(image);
        model.addAttribute("imageBase64",imageBase64);
        //save to entity our result
        generation.setImage(imageBase64);

        return "index";
    }

}
