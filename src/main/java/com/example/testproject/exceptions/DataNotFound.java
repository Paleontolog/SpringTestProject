package com.example.testproject.exceptions;

public class DataNotFound extends DatabaseException {
    private static final String DEFAULT_MESSAGE = "%s not found";

    public DataNotFound(String message) {
        super(DEFAULT_MESSAGE.formatted(message));
    }
}
