package com.aygo.aiintegration.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@RestController
@RequestMapping("/chatgpt")
public class ChatGPTController {

    private final HttpClient client;
    private final String endpoint;
    private final String apikey;

    public ChatGPTController(@Value("${api.chatgpt.url}") String apiUrl,
            @Value("${api.chatgpt.key}") String apiKey) {
        this.client = HttpClient.newHttpClient();
        this.endpoint = apiUrl;
        this.apikey = apiKey;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody String input) {
        System.out.println("ChatGPTController: Procesando solicitud para GPT.");

        String requestBodyJson = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \""
                + input.replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t") + "\"}] }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apikey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                try {
                    JsonObject errorJson = JsonParser.parseString(response.body()).getAsJsonObject();
                    JsonObject errorDetails = errorJson.getAsJsonObject("error");
                    String errorMessage = errorDetails != null && errorDetails.has("message")
                            ? errorDetails.get("message").getAsString()
                            : "Error desconocido de la API.";
                    System.err.println("Error de API: Código " + response.statusCode() + " - " + errorMessage);
                    return ResponseEntity.status(response.statusCode()).body("Error de OpenAI: " + errorMessage);
                } catch (JsonSyntaxException | IllegalStateException jsonParseEx) {
                    System.err.println("Error de API (código " + response.statusCode()
                            + "): No se pudo parsear el JSON de error. Cuerpo: " + response.body());
                    return ResponseEntity.status(response.statusCode()).body(
                            "Error de OpenAI con código " + response.statusCode() + ". Respuesta: " + response.body());
                }
            }

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonResponse.has("error")) {
                JsonObject error = jsonResponse.getAsJsonObject("error");
                String mensaje = error.has("message") ? error.get("message").getAsString()
                        : "Error desconocido de OpenAI.";
                System.err.println("Error en la respuesta JSON de OpenAI: " + mensaje);
                return ResponseEntity.status(500).body("Error de OpenAI: " + mensaje);
            }

            if (!jsonResponse.has("choices") || jsonResponse.getAsJsonArray("choices").size() == 0) {
                System.err.println("Error: La respuesta de OpenAI no contiene 'choices' o está vacía.");
                return ResponseEntity.status(500).body("Error: La respuesta de la IA no contiene sugerencias válidas.");
            }

            String content = jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

            return ResponseEntity.ok(content);

        } catch (Exception e) {
            System.err.println("Error al conectar o procesar con OpenAI: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al conectar con OpenAI: " + e.getMessage());
        }
    }

    public String generateRawResponse(String input) throws Exception {
        String requestBodyJson = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \""
                + input.replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t") + "\"}] }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apikey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            String errorMessage = "Error de API: Código " + response.statusCode() + ". Cuerpo: " + response.body();
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        if (jsonResponse.has("error")) {
            JsonObject error = jsonResponse.getAsJsonObject("error");
            String mensaje = error.has("message") ? error.get("message").getAsString() : "Error desconocido de OpenAI.";
            String fullError = "Error en la respuesta JSON de OpenAI: " + mensaje;
            System.err.println(fullError);
            throw new RuntimeException(fullError);
        }

        if (!jsonResponse.has("choices") || jsonResponse.getAsJsonArray("choices").size() == 0) {
            throw new RuntimeException("Error: La respuesta no contiene sugerencias válidas.");
        }

        return jsonResponse.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString();
    }
}