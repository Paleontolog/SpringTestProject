package com.example.testproject.controllers;

import com.example.testproject.exceptions.DatabaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    private record Response(List<String> errors) {
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors =
                ex.getBindingResult()
                        .getFieldErrors().stream()
                        .filter(x -> x.getDefaultMessage() != null)
                        .map(x -> "%s: %s".formatted(x.getField(), x.getDefaultMessage()))
                        .toList();

        return new ResponseEntity<>(new Response(errors), status);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Response> handleDatabaseExceptions(DatabaseException ex) {
        return new ResponseEntity<>(new Response(List.of(ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new Response(List.of(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response(List.of(ex.getMessage())), status);
    }
}
