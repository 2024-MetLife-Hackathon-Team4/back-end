package com.metlife.hackathon.controller;

import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AiController {
    private final ChatClient client;

    public AiController(ChatClient client) {
        this.client = client;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return client.call(message);
    }
}
