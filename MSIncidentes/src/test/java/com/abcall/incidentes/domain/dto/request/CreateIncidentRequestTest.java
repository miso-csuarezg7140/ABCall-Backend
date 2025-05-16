package com.abcall.incidentes.domain.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CreateIncidentRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validationSucceedsWhenAllFieldsAreValid() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("ABCDE12345");
        request.setClientDocumentNumber("123456789");
        request.setDescription("Valid description");

        Set<ConstraintViolation<CreateIncidentRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validationFailsWhenUserDocumentTypeIsInvalid() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setClientDocumentNumber("1010101010");
        request.setUserDocumentNumber("1010342987");
        request.setDescription("abc");
        request.setUserDocumentType("0");

        Set<ConstraintViolation<CreateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo tipoDocumentoUsuario no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenUserDocumentNumberIsInvalid() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setDescription("abc");
        request.setClientDocumentNumber("1010101010");
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("123");

        Set<ConstraintViolation<CreateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo numDocumentoUsuario no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenClientDocumentNumberIsInvalid() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("1013452312");
        request.setDescription("abc");
        request.setClientDocumentNumber("12345678");

        Set<ConstraintViolation<CreateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo numDocumentoCliente no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenDescriptionIsBlank() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setClientDocumentNumber("1010101010");
        request.setUserDocumentNumber("51979654");
        request.setUserDocumentType("1");
        request.setDescription("");

        Set<ConstraintViolation<CreateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo descripcion no cumple las validaciones.", violations.iterator().next().getMessage());
    }
}