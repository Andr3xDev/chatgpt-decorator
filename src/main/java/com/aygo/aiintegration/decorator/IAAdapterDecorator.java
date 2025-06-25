package com.aygo.aiintegration.decorator;

import com.aygo.aiintegration.adapter.IAiAdapter;

public abstract class IAAdapterDecorator implements IAiAdapter {
    protected IAiAdapter decoratedAdapter; // El objeto IAiAdapter que estamos decorando

    public IAAdapterDecorator(IAiAdapter decoratedAdapter) {
        this.decoratedAdapter = decoratedAdapter;
    }

    @Override
    public String generateResponse(String input) {
        // Delega la llamada al método original del objeto decorado
        return decoratedAdapter.generateResponse(input);
    }

    @Override
    public String getEstado() {
        // Delega la llamada al método original del objeto decorado
        return decoratedAdapter.getEstado();
    }
}