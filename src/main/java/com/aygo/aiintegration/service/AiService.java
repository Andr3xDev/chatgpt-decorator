package com.aygo.aiintegration.service;

import com.aygo.aiintegration.adapter.IAiAdapter;
import com.aygo.aiintegration.analyzer.InputAnalyzer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final List<IAiAdapter> adapters;

    public AiService(List<IAiAdapter> adapters) {
        this.adapters = adapters;

        adapters.stream()
                .filter(adapter -> adapter.getEstado().equals("general"))
                .findFirst()
                .ifPresent(InputAnalyzer::setChatGptAdapter);
    }

    public String generateResponse(String input) {
        String cleanedInput = InputAnalyzer.cleanInput(input);
        boolean isCode = InputAnalyzer.isCode(cleanedInput);
        String newInput = InputAnalyzer.improveInput(cleanedInput, isCode);
        return adapters.stream()
                .filter(adapter -> isCode ? adapter.getEstado().equals("código") : adapter.getEstado().equals("general"))
                .findFirst()
                .map(adapter -> adapter.generateResponse(newInput))
                .orElseThrow(() -> new IllegalArgumentException("No suitable adapter found."));
    }
}
