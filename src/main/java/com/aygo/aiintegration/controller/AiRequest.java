package com.aygo.aiintegration.controller;

public class AiRequest {

    private String input;
    private String aiType;
    private boolean shortResponse = false;
    private boolean truncateResponse = false;
    private int maxLength = 100;

    // Getters y Setters
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getAiType() {
        return aiType;
    }

    public void setAiType(String aiType) {
        this.aiType = aiType;
    }

    public boolean isShortResponse() {
        return shortResponse;
    }

    public void setShortResponse(boolean shortResponse) {
        this.shortResponse = shortResponse;
    }

    public boolean isTruncateResponse() {
        return truncateResponse;
    }

    public void setTruncateResponse(boolean truncateResponse) {
        this.truncateResponse = truncateResponse;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

}