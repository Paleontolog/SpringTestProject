package com.example.testproject.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChangeQuoteDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testContentNotNull() throws Exception {
        var dto = new ChangeQuoteDto(1L, "text");
        var errors = validator.validate(dto);
        assertTrue(errors.isEmpty());
    }


    @Test
    void testContentNullable() throws Exception {
        var dto = new ChangeQuoteDto(1L, null);
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }
}