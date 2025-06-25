package com.aygo.aiintegration.service;

import com.aygo.aiintegration.adapter.IAiAdapter;
import com.aygo.aiintegration.analyzer.InputAnalyzer;
import com.aygo.aiintegration.decorator.ShortResponsePromptDecorator;
import com.aygo.aiintegration.decorator.TruncateResponseDecorator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final Map<String, IAiAdapter> aiAdapters;

    public AiService(List<IAiAdapter> adapters) {
        this.aiAdapters = adapters.stream()
                .collect(Collectors.toMap(IAiAdapter::getEstado, Function.identity()));

        aiAdapters.values().stream()
                .filter(adapter -> adapter.getEstado().equals("general"))
                .findFirst()
                .ifPresent(InputAnalyzer::setChatGptAdapter);
    }

    public String generateResponse(String input, AiServiceOptions options) {
        String cleanedInput = InputAnalyzer.cleanInput(input);
        boolean isCode = InputAnalyzer.isCode(cleanedInput);
        String newInput = InputAnalyzer.improveInput(cleanedInput, isCode);

        String targetAdapterState = isCode ? "código" : "general";
        IAiAdapter baseAdapter = aiAdapters.get(targetAdapterState);

        if (baseAdapter == null) {
            throw new IllegalArgumentException(
                    "No se encontró un adaptador adecuado para el estado: " + targetAdapterState);
        }

        IAiAdapter currentAdapter = baseAdapter;

        if (options.isShortResponse()) {
            currentAdapter = new ShortResponsePromptDecorator(currentAdapter);
        }
        if (options.isTruncateResponse()) {
            currentAdapter = new TruncateResponseDecorator(currentAdapter, options.getMaxLength());
        }

        return currentAdapter.generateResponse(newInput);
    }

    public static class AiServiceOptions {
        private boolean shortResponse = false;
        private boolean truncateResponse = false;
        private int maxLength = 100;

        public boolean isShortResponse() {
            return shortResponse;
        }

        public AiServiceOptions setShortResponse(boolean shortResponse) {
            this.shortResponse = shortResponse;
            return this;
        }

        public boolean isTruncateResponse() {
            return truncateResponse;
        }

        public AiServiceOptions setTruncateResponse(boolean truncateResponse) {
            this.truncateResponse = truncateResponse;
            return this;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public AiServiceOptions setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }
    }
}