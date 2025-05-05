package com.abcall.auth.domain.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void whenAllFieldsValidThenNoViolations() {
        LoginRequest request = new LoginRequest(
                "1",
                "ABC123",
                "password123",
                "cliente"
        );

        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenDefaultConstructorThenDefaultDocumentType() {
        LoginRequest request = new LoginRequest();
        assertEquals("1", request.getDocumentType());
    }

    @Test
    void whenInvalidDocumentTypeThenViolation() {
        LoginRequest request = new LoginRequest(
                "7",
                "ABC123",
                "password123",
                "cliente"
        );

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("documentType")));
    }

    @Test
    void whenInvalidDocumentNumberThenViolation() {
        LoginRequest request = new LoginRequest(
                "1",
                "abc",  // lowercase not allowed
                "password123",
                "cliente"
        );

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("documentNumber")));
    }

    @Test
    void whenBlankPasswordThenViolation() {
        LoginRequest request = new LoginRequest(
                "1",
                "ABC123",
                "",
                "cliente"
        );

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void whenInvalidUserTypeThenViolation() {
        LoginRequest request = new LoginRequest(
                "1",
                "ABC123",
                "password123",
                "admin"  // not allowed value
        );

        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("userType")));
    }

    @Test
    void whenSettersThenValuesUpdated() {
        LoginRequest request = new LoginRequest();
        request.setDocumentType("2");
        request.setDocumentNumber("ABC123");
        request.setPassword("password123");
        request.setUserType("agente");

        assertEquals("2", request.getDocumentType());
        assertEquals("ABC123", request.getDocumentNumber());
        assertEquals("password123", request.getPassword());
        assertEquals("agente", request.getUserType());
    }
}