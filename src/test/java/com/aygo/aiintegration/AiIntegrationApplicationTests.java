package com.aygo.aiintegration;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AiIntegrationApplicationTests {

    @Value("${api.chatgpt.url}")
    private String chatGptUrl;

    @Value("${api.copilot.url}")
    private String copilotUrl;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
    /*
    @Test
    void contextLoads() {
        // Verifica que el contexto de la aplicación se carga correctamente
    }

    @Test
    void testChatGptEndpoint() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"response\": \"Respuesta de ChatGPT simulada\"}")
                .addHeader("Content-Type", "application/json"));

        TestRestTemplate restTemplate = new TestRestTemplate();
        String input = "¿Qué es la inteligencia artificial?";
        ResponseEntity<String> response = restTemplate.postForEntity("/chatgpt/generate", input, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Respuesta de ChatGPT simulada");
    }

    @Test
    void testCopilotEndpoint() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"response\": \"Código generado por Copilot simulado\"}")
                .addHeader("Content-Type", "application/json"));

        TestRestTemplate restTemplate = new TestRestTemplate();
        String input = "Escribe una función en Java para sumar dos números.";
        ResponseEntity<String> response = restTemplate.postForEntity("/copilot/generate", input, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Código generado por Copilot simulado");
    }*/
}
