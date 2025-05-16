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
class UpdateIncidentRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final String incidentId = "123456";
    private final String userDocumentType = "1";
    private final String userDocumentNumber = "123456789";
    private final String clientDocumentNumber = "987654321";
    private final String estado = "SOLUCIONADO";

    @Test
    void validationSucceedsWhenAllFieldsAreValid() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(true);
        request.setStatus("SOLUCIONADO");
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validationFailsWhenIncidentIdIsInvalid() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId("abc");
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(true);
        request.setStatus("SOLUCIONADO");
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo idIncidente no cumple las validaciones.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenUserDocumentTypeIsInvalid() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType("0");
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(true);
        request.setStatus(estado);
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo tipoDocumentoUsuario no cumple las validaciones.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenUserDocumentNumberIsInvalid() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber("123");
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(true);
        request.setStatus(estado);
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo numDocumentoUsuario no cumple las validaciones.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenClientDocumentNumberIsInvalid() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber("12345678");
        request.setSolved(true);
        request.setStatus(estado);
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo numDocumentoCliente no cumple las validaciones.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenSolvedIsNull() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(null);
        request.setStatus(estado);
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo solucionado no cumple las validaciones.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenStatusIsBlank() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(true);
        request.setStatus("");
        request.setModifiedBy("1");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo estado no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenModifiedByIsBlank() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setIncidentId(incidentId);
        request.setUserDocumentType(userDocumentType);
        request.setUserDocumentNumber(userDocumentNumber);
        request.setClientDocumentNumber(clientDocumentNumber);
        request.setSolved(true);
        request.setStatus(estado);
        request.setModifiedBy("");

        Set<ConstraintViolation<UpdateIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El campo modificadoPor no cumple las validaciones.",
                violations.iterator().next().getMessage());
    }
}