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
        String originalResponse = super.generateResponse(input);
        if (originalResponse != null && originalResponse.length() > maxLength) {
            return originalResponse.substring(0, maxLength) + "...";
        }
        return originalResponse;
    }
}