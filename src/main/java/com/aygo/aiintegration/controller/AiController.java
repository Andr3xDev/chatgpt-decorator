package com.aygo.aiintegration.controller;

import com.aygo.aiintegration.analyzer.InputAnalyzer;
import com.aygo.aiintegration.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody String input) {
        try {
            String response = aiService.generateResponse(input);
            String response2 = InputAnalyzer.extractContentFromResponse(response);
            return ResponseEntity.ok(response2);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

