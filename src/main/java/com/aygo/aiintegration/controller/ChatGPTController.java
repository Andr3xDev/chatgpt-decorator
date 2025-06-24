package com.aygo.aiintegration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatgpt")
public class ChatGPTController {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${api.chatgpt.key}")
    private String apiKey;

    @Value("${api.chatgpt.url}")
    private String apiUrl;

    public ChatGPTController(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateResponse(@RequestBody String input) {
        System.out.println("OpenIA");
        try {
            // Construye el cuerpo de la solicitud
            String requestBody = buildRequestPayload(input);

            // Realiza la solicitud al endpoint de OpenAI
            String response = webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(response);

        } catch (WebClientResponseException e) {
            // Maneja errores específicos de OpenAI
            System.err.println("Error al comunicarse con ChatGPT: " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body("Error al comunicarse con ChatGPT: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Manejo de errores generales
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }

    private String buildRequestPayload(String input) throws JsonProcessingException {
        // Estructura el JSON para el endpoint /v1/chat/completions
        Map<String, Object> payload = Map.of(
                "model", "gpt-4o",
                "messages", List.of(
                        Map.of("role", "user", "content", input)
                ),
                "max_tokens", 1000,
                "temperature", 0.7
        );

        // Convierte el mapa en un JSON válido
        return objectMapper.writeValueAsString(payload);
    }
}