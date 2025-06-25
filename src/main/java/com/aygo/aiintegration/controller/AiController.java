package com.aygo.aiintegration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aygo.aiintegration.analyzer.InputAnalyzer;
import com.aygo.aiintegration.service.AiService;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody AiRequest request) {
        try {
            AiService.AiServiceOptions options = new AiService.AiServiceOptions()
                    .setShortResponse(request.isShortResponse())
                    .setTruncateResponse(request.isTruncateResponse())
                    .setMaxLength(request.getMaxLength());

            String response = aiService.generateResponse(
                    request.getInput(),
                    options);

            String finalResponse = InputAnalyzer.extractContentFromResponse(response);

            return ResponseEntity.ok(finalResponse);

        } catch (IllegalArgumentException e) {
            System.err.println("Error de solicitud: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error en la solicitud: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }
}