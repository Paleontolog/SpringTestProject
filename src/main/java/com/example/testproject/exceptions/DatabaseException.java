package com.example.testproject.exceptions;

public class DatabaseException extends RuntimeException {

    DatabaseException(String message, Exception exception) {
        super(message, exception);
    }

    DatabaseException(Exception exception) {
        super(exception);
    }

    public DatabaseException(String message) {
        super(message);
    }
}

