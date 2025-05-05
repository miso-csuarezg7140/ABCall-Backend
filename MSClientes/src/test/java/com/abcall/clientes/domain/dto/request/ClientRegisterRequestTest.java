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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class ClientRegisterRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void noArgsConstructor_CreatesEmptyObject() {
        // Act
        ClientRegisterRequest request = new ClientRegisterRequest();

        // Assert
        assertNull(request.getDocumentNumber());
        assertNull(request.getSocialReason());
        assertNull(request.getEmail());
        assertNull(request.getPassword());
    }

    @Test
    void allArgsConstructor_CreatesObjectWithCorrectValues() {
        // Arrange
        String documentNumber = "1234567890";
        String socialReason = "Test Company";
        String email = "test@example.com";
        String password = "Password123!";

        // Act
        ClientRegisterRequest request = new ClientRegisterRequest(documentNumber, socialReason, email, password);

        // Assert
        assertEquals(documentNumber, request.getDocumentNumber());
        assertEquals(socialReason, request.getSocialReason());
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }

    @Test
    void builder_CreatesObjectWithCorrectValues() {
        // Arrange
        String documentNumber = "1234567890";
        String socialReason = "Test Company";
        String email = "test@example.com";
        String password = "Password123!";

        // Act
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber(documentNumber)
                .socialReason(socialReason)
                .email(email)
                .password(password)
                .build();

        // Assert
        assertEquals(documentNumber, request.getDocumentNumber());
        assertEquals(socialReason, request.getSocialReason());
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }

    @Test
    void validation_ValidRequest_NoViolations() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    // Document Number Tests
    @Test
    void validation_NullDocumentNumber_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber(null)
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_EmptyDocumentNumber_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_InvalidDocumentNumberPattern_HasViolation() {
        // Arrange - VALIDACION_DOCUMENTO_CLIENTE = "^[0-9]{9,10}$"
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("ABC123")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validation_DocumentNumberTooShort_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("12345678")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_DocumentNumberTooLong_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("12345678901")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    // Social Reason Tests
    @Test
    void validation_NullSocialReason_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason(null)
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo razonSocial no cumple las validaciones.")));
    }

    @Test
    void validation_EmptySocialReason_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("")
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo razonSocial no cumple las validaciones.")));
    }

    @Test
    void validation_SocialReasonTooLong_HasViolation() {
        // Arrange - Social reason > 50 characters
        String longSocialReason = "A".repeat(51);
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason(longSocialReason)
                .email("test@example.com")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo razonSocial no cumple las validaciones.")));
    }

    // Email Tests
    @Test
    void validation_NullEmail_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email(null)
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo correo no cumple las validaciones.")));
    }

    @Test
    void validation_EmptyEmail_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_InvalidEmailFormat_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("invalid-email")
                .password("Password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo correo no cumple las validaciones.")));
    }

    @Test
    void validation_ValidEmailFormats_NoViolations() {
        // Arrange - Test various valid email formats
        String[] validEmails = {
                "test@example.com",
                "test.name@example.com",
                "test+label@example.com",
                "test123@example.co.uk",
                "test_name@example-domain.com"
        };

        for (String email : validEmails) {
            ClientRegisterRequest request = ClientRegisterRequest.builder()
                    .documentNumber("1234567890")
                    .socialReason("Test Company")
                    .email(email)
                    .password("Password123!")
                    .build();

            // Act
            Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

            // Assert
            assertTrue(violations.isEmpty(), "Email should be valid: " + email);
        }
    }

    // Password Tests
    @Test
    void validation_NullPassword_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password(null)
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo contrasena no cumple las validaciones.")));
    }

    @Test
    void validation_EmptyPassword_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_InvalidPasswordPattern_HasViolation() {
        // Arrange - Password missing required elements
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("simple")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo contrasena no cumple las validaciones.")));
    }

    @Test
    void validation_PasswordMissingUppercase_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("password123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_PasswordMissingLowercase_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("PASSWORD123!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_PasswordMissingDigit_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_PasswordMissingSpecialChar_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Password123")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_PasswordTooShort_HasViolation() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("1234567890")
                .socialReason("Test Company")
                .email("test@example.com")
                .password("Pass1!")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_AllFieldsInvalid_HasMultipleViolations() {
        // Arrange
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("ABC")
                .socialReason("")
                .email("invalid-email")
                .password("pass")
                .build();

        // Act
        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.size() >= 4);
    }

    @Test
    void setters_UpdateValuesCorrectly() {
        // Arrange
        ClientRegisterRequest request = new ClientRegisterRequest();
        String documentNumber = "1234567890";
        String socialReason = "Test Company";
        String email = "test@example.com";
        String password = "Password123!";

        // Act
        request.setDocumentNumber(documentNumber);
        request.setSocialReason(socialReason);
        request.setEmail(email);
        request.setPassword(password);

        // Assert
        assertEquals(documentNumber, request.getDocumentNumber());
        assertEquals(socialReason, request.getSocialReason());
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }

    @Test
    void jsonProperties_CorrectlyMapped() {
        // This test verifies the annotations are present
        try {
            var field = ClientRegisterRequest.class.getDeclaredField("documentNumber");
            assertTrue(field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class));

            field = ClientRegisterRequest.class.getDeclaredField("socialReason");
            assertTrue(field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class));

            field = ClientRegisterRequest.class.getDeclaredField("email");
            assertTrue(field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class));

            field = ClientRegisterRequest.class.getDeclaredField("password");
            assertTrue(field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class));
        } catch (NoSuchFieldException e) {
            fail("Field not found: " + e.getMessage());
        }
    }
}