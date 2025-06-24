package com.aygo.aiintegration.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("/copilot")
public class CopilotController {

    private final WebClient webClient;

    @Value("${api.copilot.key}")
    private String apiKey;

    @Value("${api.copilot.url}")
    private String apiUrl;

    public CopilotController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateResponse(@RequestBody String input) {
        System.out.println("Azure OpenAI");
        try {
            // Construye el cuerpo de la solicitud
            String requestBody = buildRequestPayload(input);

            // Realiza la solicitud a la API de Copilot
            String response = webClient.post()
                    .uri(apiUrl)
                    .header("API-KEY",  apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(response);

        } catch (WebClientResponseException e) {
            // Maneja errores específicos de Copilot
            System.err.println("Error al comunicarse con Azure OpenIA: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body("Error al comunicarse con Azure OAI: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Manejo de errores generales
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }

    private String buildRequestPayload(String input) {
        // Construye el cuerpo del JSON para la API de Copilot
        return """
        {
            "messages": [
                { "role": "user", "content": "%s" }
            ],
            "temperature": 0.7,
            "max_tokens": 1000
        }
        """.formatted(input);
    }
}
