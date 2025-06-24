package com.aygo.aiintegration.adapter;

import com.aygo.aiintegration.controller.ChatGPTController;
import org.springframework.stereotype.Component;

@Component
public class ChatGptAdapter implements IAiAdapter {

    private final ChatGPTController controller;

    public ChatGptAdapter(ChatGPTController controller) {
        this.controller = controller;
    }

    @Override
    public String generateResponse(String input) {
        return controller.generateResponse(input).getBody();
    }

    @Override
    public String getEstado() {
        return "general";
    }
}
