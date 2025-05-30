package com.example.usersubscriptionservice;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RequestDataValidationTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testEmailIsInvalid() {
        var invalidRequest = new RequestUserDTO(
                "ValidName",
                "ValidLastName",
                "invalid_email"
        );

        Set<ConstraintViolation<RequestUserDTO>> violations = validator.validate(invalidRequest);

        assertEquals(1, violations.size());
        assertEquals("Некорректный формат email", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailIsBlank() {
        var invalidRequest = new RequestUserDTO(
                "ValidName",
                "ValidLastName",
                ""
        );

        Set<ConstraintViolation<RequestUserDTO>> violations = validator.validate(invalidRequest);

        assertEquals(1, violations.size());
        assertEquals("Email не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void testAllFieldsAreValid() {
        var validRequest = new RequestUserDTO(
                "ValidName",
                "ValidLastName",
                "valid@example.com"
        );

        Set<ConstraintViolation<RequestUserDTO>> violations = validator.validate(validRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testAllFieldsAreBlank() {
        var validRequest = new RequestUserDTO(
                "",
                "",
                ""
        );
        Set<ConstraintViolation<RequestUserDTO>> violations = validator.validate(validRequest);

        assertEquals(4, violations.size());
    }
}
