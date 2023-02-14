package com.example.testproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeQuoteDto(
        Long id,

        @NotBlank
        @NotNull
        String content
) {
}
