package com.aygo.aiintegration.adapter;

import com.aygo.aiintegration.controller.CopilotController;
import org.springframework.stereotype.Component;

@Component
public class CopilotAdapter implements IAiAdapter {

    private final CopilotController controller;

    public CopilotAdapter(CopilotController controller) {
        this.controller = controller;
    }

    @Override
    public String generateResponse(String input) {
        return controller.generateResponse(input).getBody();
    }

    @Override
    public String getEstado() {
        return "código";
    }
}
