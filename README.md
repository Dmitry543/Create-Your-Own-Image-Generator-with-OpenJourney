# How to write an absolutely free Image Generator on Spring Boot

### About
This is a repository with a demo project for an article
with a comprehensive guide.

The demo project is illustrating the way to use
the Inference API of the Hugging Face platform, send requests
and get responses with the HuggingFaceModelClient from any text-to-image  
AI model, which is loaded on Inference API.

### Getting started
This section explains how to  use HuggingFaceModelClient in your Spring Boot projects.

#### Usage
You can inject the client in your controller or service in the following way:

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
