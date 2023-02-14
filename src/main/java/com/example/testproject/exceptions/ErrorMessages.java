package com.example.testproject.exceptions;

public enum ErrorMessages {
    QUOTE_INFO("Quote with id: '%d'");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object data) {
        return message.formatted(data);
    }
}
