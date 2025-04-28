package com.abcall.incidentes.domain.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActualizarIncidenteRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldFailValidationWhenIdIncidenteIsBlank(String invalidIdIncidente) {
        ActualizarIncidenteRequest request = new ActualizarIncidenteRequest();
        request.setIdIncidente(invalidIdIncidente);

        Set<ConstraintViolation<ActualizarIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo idIncidente no puede ser nulo.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123abc"})
    void shouldFailValidationWhenIdIncidenteIsNotNumeric(String invalidIdIncidente) {
        ActualizarIncidenteRequest request = new ActualizarIncidenteRequest();
        request.setIdIncidente(invalidIdIncidente);

        Set<ConstraintViolation<ActualizarIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo idIncidente debe ser numérico.")));
    }

    @Test
    void shouldFailValidationWhenSolucionadoIsNull() {
        ActualizarIncidenteRequest request = new ActualizarIncidenteRequest();
        request.setSolucionado(null);

        Set<ConstraintViolation<ActualizarIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo solucionado no puede ser nulo.")));
    }

    @Test
    void shouldPassValidationWhenAllFieldsAreValid() {
        ActualizarIncidenteRequest request = new ActualizarIncidenteRequest();
        request.setIdIncidente("123");
        request.setTipoDocumentoUsuario("DNI");
        request.setNumDocumentoUsuario("456789");
        request.setNumDocumentoCliente("987654");
        request.setSolucionado(true);
        request.setEstado("Activo");
        request.setModificadoPor("Admin");

        Set<ConstraintViolation<ActualizarIncidenteRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }
}