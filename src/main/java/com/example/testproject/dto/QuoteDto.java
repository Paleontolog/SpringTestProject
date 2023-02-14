package com.example.testproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record QuoteDto(
        Long id,
        String user,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        Date lastChangeTime,
        Long count
) {
}

