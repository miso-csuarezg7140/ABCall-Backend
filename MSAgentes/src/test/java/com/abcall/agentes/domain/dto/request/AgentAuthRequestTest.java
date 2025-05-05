package com.abcall.agentes.domain.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@ExtendWith(MockitoExtension.class)
class AgentAuthRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void allArgsConstructorCreatesObjectWithCorrectValues() {
        AgentAuthRequest request = new AgentAuthRequest("DNI", "12345678", "password123");

        assertEquals("DNI", request.getDocumentType());
        assertEquals("12345678", request.getDocumentNumber());
        assertEquals("password123", request.getPassword());
    }

    @Test
    void noArgsConstructorCreatesObjectWithNullFields() {
        AgentAuthRequest request = new AgentAuthRequest();

        assertNull(request.getDocumentType());
        assertNull(request.getDocumentNumber());
        assertNull(request.getPassword());
    }

    @Test
    void settersModifyFieldsCorrectly() {
        AgentAuthRequest request = new AgentAuthRequest();
        request.setDocumentType("PASSPORT");
        request.setDocumentNumber("98765432");
        request.setPassword("securePass");

        assertEquals("PASSPORT", request.getDocumentType());
        assertEquals("98765432", request.getDocumentNumber());
        assertEquals("securePass", request.getPassword());
    }

    @Test
    void jsonPropertiesMapToSpanishFieldNames() throws JsonProcessingException {
        AgentAuthRequest request = new AgentAuthRequest();
        request.setDocumentType("DNI");
        request.setDocumentNumber("12345678");
        request.setPassword("securePass");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);

        assertTrue(json.contains("\"tipoDocumento\":\"DNI\""));
        assertTrue(json.contains("\"numeroDocumento\":\"12345678\""));
        assertTrue(json.contains("\"contrasena\":\"securePass\""));
    }

    @Test
    void validationFailsForEmptyDocumentType() {
        AgentAuthRequest request = new AgentAuthRequest("", "12345678", "password");
        Set<ConstraintViolation<AgentAuthRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo tipoDocumento no cumple las validaciones.")));
    }

    @Test
    void validationFailsForEmptyDocumentNumber() {
        AgentAuthRequest request = new AgentAuthRequest("DNI", "", "password");
        Set<ConstraintViolation<AgentAuthRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo numeroDocumento no cumple las validaciones.")));
    }

    @Test
    void validationFailsForEmptyPassword() {
        AgentAuthRequest request = new AgentAuthRequest("DNI", "12345678", "");
        Set<ConstraintViolation<AgentAuthRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El campo contrasena no cumple las validaciones.")));
    }
}