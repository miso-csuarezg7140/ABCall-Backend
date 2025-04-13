package com.abcall.clientes.domain.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientRegisterRequestTest {

    private final Validator validator;

    public ClientRegisterRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void validationFails_WhenDocumentNumberIsBlank() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void validationFails_WhenDocumentNumberContainsNonDigits() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123ABC")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void validationFails_WhenSocialReasonIsBlank() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("")
                .email("abc@corp.com")
                .password("password123")
                .build();

        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void validationFails_WhenEmailIsInvalid() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("invalid-email")
                .password("password123")
                .build();

        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void validationFails_WhenPasswordIsTooShort() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("short")
                .build();

        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void validationPasses_WhenAllFieldsAreValid() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        Set<ConstraintViolation<ClientRegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void gettersAndSetters_WorkCorrectly() {
        ClientRegisterRequest request = new ClientRegisterRequest();
        request.setDocumentNumber("123456789");
        request.setSocialReason("ABC Corp");
        request.setEmail("abc@corp.com");
        request.setPassword("password123");

        assertEquals("123456789", request.getDocumentNumber());
        assertEquals("ABC Corp", request.getSocialReason());
        assertEquals("abc@corp.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    @Test
    void builder_CreatesInstanceWithAllFields() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        assertEquals("123456789", request.getDocumentNumber());
        assertEquals("ABC Corp", request.getSocialReason());
        assertEquals("abc@corp.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    @Test
    void noArgsConstructor_CreatesEmptyInstance() {
        ClientRegisterRequest request = new ClientRegisterRequest();

        assertNull(request.getDocumentNumber());
        assertNull(request.getSocialReason());
        assertNull(request.getEmail());
        assertNull(request.getPassword());
    }

    @Test
    void allArgsConstructor_CreatesPopulatedInstance() {
        ClientRegisterRequest request = new ClientRegisterRequest(
                "123456789",
                "ABC Corp",
                "abc@corp.com",
                "password123"
        );

        assertEquals("123456789", request.getDocumentNumber());
        assertEquals("ABC Corp", request.getSocialReason());
        assertEquals("abc@corp.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }
}