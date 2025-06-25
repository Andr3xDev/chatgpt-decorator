package com.aygo.aiintegration.decorator;

import com.aygo.aiintegration.adapter.IAiAdapter;

public class ShortResponsePromptDecorator extends IAAdapterDecorator {

    public ShortResponsePromptDecorator(IAiAdapter decoratedAdapter) {
        super(decoratedAdapter);
    }

    @Override
    public String generateResponse(String input) {
        String shortPrompt = "Por favor, responde de forma concisa y breve, menos de 50 palabras: " + input;
        return super.generateResponse(shortPrompt);
    }
}