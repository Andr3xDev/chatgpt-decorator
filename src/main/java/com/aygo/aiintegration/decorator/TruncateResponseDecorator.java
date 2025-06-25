package com.aygo.aiintegration.decorator;

import com.aygo.aiintegration.adapter.IAiAdapter;

public class TruncateResponseDecorator extends IAAdapterDecorator {

    private final int maxLength;

    public TruncateResponseDecorator(IAiAdapter decoratedAdapter, int maxLength) {
        super(decoratedAdapter);
        this.maxLength = maxLength;
    }

    @Override
    public String generateResponse(String input) {
        String originalResponse = super.generateResponse(input); // Obtiene la respuesta
        if (originalResponse != null && originalResponse.length() > maxLength) {
            // Trunca la respuesta y añade puntos suspensivos
            return originalResponse.substring(0, maxLength) + "...";
        }
        return originalResponse;
    }
}