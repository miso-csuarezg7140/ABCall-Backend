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
class ConsultIncidentRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validationSucceedsWhenAllFieldsAreValid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("ABCDE12345");
        request.setClientDocumentNumber("123456789");
        request.setStatus("EN PROCESO");
        request.setStartDate("2023/01/01");
        request.setEndDate("2023/12/31");
        request.setPage("1");
        request.setPageSize("10");
        request.setDownload(false);

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validationFailsWhenUserDocumentTypeIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setUserDocumentType("0");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro tipoDocUsuario no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenUserDocumentNumberIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setUserDocumentNumber("123");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro numeroDocUsuario no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenClientDocumentNumberIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setClientDocumentNumber("12345678");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro numeroDocCliente no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenStatusIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStatus("INVALIDO");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro estado no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenStartDateIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStartDate("2023-01-01");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro fechaInicio no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenEndDateIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setEndDate("2023-12-31");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro fechaFin no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenPageIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setPage("012");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El parámetro pagina no cumple las validaciones.", violations.iterator().next().getMessage());
    }

    @Test
    void validationFailsWhenPageSizeIsInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setPageSize("1234");

        Set<ConstraintViolation<ConsultIncidentRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("El paránetro tamanioPagina no cumple las validaciones.", violations.iterator().next().getMessage());
    }
}