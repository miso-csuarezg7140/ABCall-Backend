package com.abcall.clientes.domain.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientAuthRequestTest {

    private final Validator validator;

    public ClientAuthRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void clientAuthRequest_ValidatesSuccessfully_WhenFieldsAreValid() {
        ClientAuthRequest request = new ClientAuthRequest("123456789", "password123");

        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void clientAuthRequest_ReturnsViolation_WhenUsernameIsBlank() {
        ClientAuthRequest request = new ClientAuthRequest("", "password123");

        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo username no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void clientAuthRequest_ReturnsViolation_WhenUsernameIsNotNumeric() {
        ClientAuthRequest request = new ClientAuthRequest("abc123", "password123");

        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo username no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void clientAuthRequest_ReturnsViolation_WhenPasswordIsBlank() {
        ClientAuthRequest request = new ClientAuthRequest("123456789", "");

        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo password no cumple las validaciones.", violations.iterator().next().getMessage());
    }
}