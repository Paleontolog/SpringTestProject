package com.example.testproject.exceptions;

public class DataAlreadyExist extends DatabaseException {
    private static final String DEFAULT_MESSAGE = "%s already exist";

    public DataAlreadyExist(String message) {
        super(DEFAULT_MESSAGE.formatted(message));
    }

}
