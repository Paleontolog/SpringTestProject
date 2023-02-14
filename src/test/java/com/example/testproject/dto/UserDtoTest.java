package com.example.testproject.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private static Validator validator;
    private final UserDto userDto = new UserDto(1L, "name", "test@yandex.ru", "password", LocalDateTime.of(1984, 12, 12, 12, 12));

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testContentNotNull() {
        var errors = validator.validate(userDto);
        assertTrue(errors.isEmpty());
    }


    @Test
    void testContentNameNullable() throws Exception {
        var dto = new UserDto(
                userDto.id(),
                null,
                userDto.email(),
                userDto.password(),
                userDto.creationDate()
        );
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }



    @Test
    void testContentEmailNullable() throws Exception {
        var dto = new UserDto(
                userDto.id(),
                userDto.name(),
                null,
                userDto.password(),
                userDto.creationDate()
        );
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testContentEmailIncorrect() throws Exception {
        var dto = new UserDto(
                userDto.id(),
                userDto.name(),
                "incorrect_email",
                userDto.password(),
                userDto.creationDate()
        );
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testContentPasswordNullable() throws Exception {
        var dto = new UserDto(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                null,
                userDto.creationDate()
        );
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }


    @Test
    void testContentPasswordShort() throws Exception {
        var dto = new UserDto(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                "pass",
                userDto.creationDate()
        );
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testContentPasswordLong() throws Exception {
        var dto = new UserDto(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                "passsssssssssssssssssssssword",
                userDto.creationDate()
        );
        var errors = validator.validate(dto);
        assertFalse(errors.isEmpty());
    }
}