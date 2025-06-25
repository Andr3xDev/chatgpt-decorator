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
        try {
            return controller.generateRawResponse(input);
        } catch (Exception e) {
            System.err.println("Error en ChatGptAdapter al generar respuesta: " + e.getMessage());
            return "Error al generar respuesta de ChatGPT: " + e.getMessage();
        }
    }

    @Override
    public String getEstado() {
        return "general";
    }
}