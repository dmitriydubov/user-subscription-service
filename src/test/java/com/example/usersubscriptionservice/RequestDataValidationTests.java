package com.example.usersubscriptionservice;

import com.example.usersubscriptionservice.dto.RequestSubscriptionDTO;
import com.example.usersubscriptionservice.dto.RequestUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

    @Test
    void testSubscriptionRequestIsValid() {
        var request = new RequestSubscriptionDTO(
            "test",
            "url",
            LocalDate.now(),
            LocalDate.now().plusDays(1)
        );

        Set<ConstraintViolation<RequestSubscriptionDTO>> violations = validator.validate(request);

        assertEquals(1, violations.size());
    }

    @Test
    void testSubscriptionRequestIsInvalid() {
        var request = new RequestSubscriptionDTO(
            "",
            "",
            null,
            null
        );

        Set<ConstraintViolation<RequestSubscriptionDTO>> violations = validator.validate(request);

        assertEquals(6, violations.size());
    }
}
