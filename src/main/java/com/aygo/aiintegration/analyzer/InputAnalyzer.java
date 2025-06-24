package com.aygo.aiintegration.analyzer;

import com.aygo.aiintegration.adapter.IAiAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InputAnalyzer {

    private static IAiAdapter chatGptAdapter;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setChatGptAdapter(IAiAdapter adapter) {
        chatGptAdapter = adapter;
    }
    
    

    public static boolean isCode(String input) {
        if (chatGptAdapter != null) {
            String query = "Esto es código o tiene que ver con código? Responde si o no sin mas texto" + input;
            String response = chatGptAdapter.generateResponse(query);
            return (extractContentFromResponse(response).toLowerCase().contains("sí")) ;
        }
        return false;
    }

    public static String cleanInput(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }
    
    public static String improveInput(String input, Boolean isCode) {
        if (chatGptAdapter != null && input.length() < 100 && !isCode) {
            String query = "Mejora esta entrada como un prompt para una IA: " + input;
            String response = chatGptAdapter.generateResponse(query);
            String content = extractContentFromResponse(response);
            return content != null ? content : input;
        }
        return input;
    
    }
    public static String extractContentFromResponse(String response) {
    try {
        // Verifica si la respuesta es JSON
        if (!response.trim().contains("{")) {
            System.err.println("Respuesta no válida para JSON: " + response);
            return null;
        }

        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode messageNode = rootNode.path("choices").get(0).path("message").path("content");

        return messageNode.asText();
    } catch (Exception e) {
        e.printStackTrace();
        return null; // Retorna null si no puede extraer el contenido
    }
}

}
