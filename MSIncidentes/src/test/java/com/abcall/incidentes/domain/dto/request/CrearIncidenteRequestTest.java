package com.abcall.incidentes.domain.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CrearIncidenteRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldPassValidationWhenAllFieldsAreValid() {
        CrearIncidenteRequest request = new CrearIncidenteRequest();
        request.setTipoDocumentoUsuario("CC");
        request.setNumDocumentoUsuario("123456789");
        request.setNumDocumentoCliente("987654321");
        request.setDescripcion("Incident description");

        Set<ConstraintViolation<CrearIncidenteRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenTipoDocumentoUsuarioIsBlank() {
        CrearIncidenteRequest request = new CrearIncidenteRequest();
        request.setTipoDocumentoUsuario("");
        request.setNumDocumentoUsuario("123456789");
        request.setNumDocumentoCliente("987654321");
        request.setDescripcion("Incident description");

        Set<ConstraintViolation<CrearIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo tipoDocumentoUsuario no puede ser nulo.")));
    }

    @Test
    void shouldFailValidationWhenNumDocumentoUsuarioIsNull() {
        CrearIncidenteRequest request = new CrearIncidenteRequest();
        request.setTipoDocumentoUsuario("CC");
        request.setNumDocumentoUsuario(null);
        request.setNumDocumentoCliente("987654321");
        request.setDescripcion("Incident description");

        Set<ConstraintViolation<CrearIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo numDocumentoUsuario no puede ser nulo.")));
    }

    @Test
    void shouldFailValidationWhenNumDocumentoUsuarioIsNotNumeric() {
        CrearIncidenteRequest request = new CrearIncidenteRequest();
        request.setTipoDocumentoUsuario("CC");
        request.setNumDocumentoUsuario("ABC123");
        request.setNumDocumentoCliente("987654321");
        request.setDescripcion("Incident description");

        Set<ConstraintViolation<CrearIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo numDocumentoUsuario debe ser numérico.")));
    }

    @Test
    void shouldFailValidationWhenDescripcionIsBlank() {
        CrearIncidenteRequest request = new CrearIncidenteRequest();
        request.setTipoDocumentoUsuario("CC");
        request.setNumDocumentoUsuario("123456789");
        request.setNumDocumentoCliente("987654321");
        request.setDescripcion("");

        Set<ConstraintViolation<CrearIncidenteRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El campo descripcion no puede ser nulo.")));
    }
}