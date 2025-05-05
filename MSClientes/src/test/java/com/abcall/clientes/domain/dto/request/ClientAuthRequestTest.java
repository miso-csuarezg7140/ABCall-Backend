package com.abcall.clientes.domain.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class ClientAuthRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void noArgsConstructor_CreatesEmptyObject() {
        // Act
        ClientAuthRequest request = new ClientAuthRequest();

        // Assert
        assertNull(request.getDocumentNumber());
        assertNull(request.getPassword());
    }

    @Test
    void allArgsConstructor_CreatesObjectWithCorrectValues() {
        // Arrange
        String documentNumber = "1234567890";
        String password = "Password123!";

        // Act
        ClientAuthRequest request = new ClientAuthRequest(documentNumber, password);

        // Assert
        assertEquals(documentNumber, request.getDocumentNumber());
        assertEquals(password, request.getPassword());
    }

    @Test
    void setters_UpdateValuesCorrectly() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest();
        String documentNumber = "1234567890";
        String password = "Password123!";

        // Act
        request.setDocumentNumber(documentNumber);
        request.setPassword(password);

        // Assert
        assertEquals(documentNumber, request.getDocumentNumber());
        assertEquals(password, request.getPassword());
    }

    @Test
    void validation_ValidRequest_NoViolations() {
        // Arrange
        // VALIDACION_DOCUMENTO_CLIENTE = "^[0-9]{9,10}$"
        // Password only requires @NotBlank now
        ClientAuthRequest request = new ClientAuthRequest("1234567890", "simplepassword");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_NullDocumentNumber_HasViolation() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest(null, "Password123!");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_EmptyDocumentNumber_HasViolation() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("", "Password123!");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_InvalidDocumentNumberPattern_HasViolation() {
        // Arrange
        // VALIDACION_DOCUMENTO_CLIENTE expects only digits, 9-10 in length
        ClientAuthRequest request = new ClientAuthRequest("ABC123", "Password123!");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_DocumentNumberTooShort_HasViolation() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("12345678", "Password123!");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_DocumentNumberTooLong_HasViolation() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("12345678901", "Password123!");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_ValidDocumentNumbers_NoViolations() {
        // Arrange - Test both 9 and 10 digit valid document numbers
        ClientAuthRequest request9Digits = new ClientAuthRequest("123456789", "Password123!");
        ClientAuthRequest request10Digits = new ClientAuthRequest("1234567890", "Password123!");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations9 = validator.validate(request9Digits);
        Set<ConstraintViolation<ClientAuthRequest>> violations10 = validator.validate(request10Digits);

        // Assert
        assertTrue(violations9.isEmpty());
        assertTrue(violations10.isEmpty());
    }

    @Test
    void validation_NullPassword_HasViolation() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("1234567890", null);

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo contrasena no cumple las validaciones.")));
    }

    @Test
    void validation_EmptyPassword_HasViolation() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("1234567890", "");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo contrasena no cumple las validaciones.")));
    }

    @Test
    void validation_SimplePassword_NoViolations() {
        // Arrange
        // Password now only needs to be not blank
        ClientAuthRequest request = new ClientAuthRequest("1234567890", "simple");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_AnyNonBlankPassword_NoViolations() {
        // Arrange - Test various password formats (all should be valid now)
        String[] validPasswords = {
                "a",                // Very short
                "password",         // All lowercase
                "PASSWORD",         // All uppercase
                "12345678",         // All numbers
                "!@#$%^&*",         // All special characters
                "simple password",  // With space
                "123",              // Short number
                "p"                 // Single character
        };

        for (String password : validPasswords) {
            ClientAuthRequest request = new ClientAuthRequest("1234567890", password);

            // Act
            Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

            // Assert
            assertTrue(violations.isEmpty(), "Password should be valid: " + password);
        }
    }

    @Test
    void validation_BothFieldsInvalid_HasMultipleViolations() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("", "");

        // Act
        Set<ConstraintViolation<ClientAuthRequest>> violations = validator.validate(request);

        // Assert
        assertEquals(3, violations.size());
    }

    @Test
    void jsonProperties_CorrectlyMapped() {
        // This test would require integration testing with Jackson
        // Here we just verify the annotations are present
        try {
            var field = ClientAuthRequest.class.getDeclaredField("documentNumber");
            assertTrue(field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class));

            field = ClientAuthRequest.class.getDeclaredField("password");
            assertTrue(field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class));
        } catch (NoSuchFieldException e) {
            fail("Field not found: " + e.getMessage());
        }
    }

    @Test
    void equals_SameObject_ReturnsTrue() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("1234567890", "Password123!");

        // Act & Assert
        assertEquals(request, request);
    }

    @Test
    void equals_DifferentObjectsSameValues_ReturnsFalse() {
        // Arrange
        ClientAuthRequest request1 = new ClientAuthRequest("1234567890", "Password123!");
        ClientAuthRequest request2 = new ClientAuthRequest("1234567890", "Password123!");

        // Act & Assert
        // Note: equals is not overridden, so different instances are not equal
        assertNotEquals(request1, request2);
    }

    @Test
    void hashCode_ConsistentForSameObject() {
        // Arrange
        ClientAuthRequest request = new ClientAuthRequest("1234567890", "Password123!");

        // Act
        int hashCode1 = request.hashCode();
        int hashCode2 = request.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}