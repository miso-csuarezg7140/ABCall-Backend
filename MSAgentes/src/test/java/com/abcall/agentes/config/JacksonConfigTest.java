package com.abcall.agentes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JacksonConfigTest {

    @Spy
    private JacksonConfig jacksonConfig;

    @Test
    void objectMapper_ShouldCreateValidInstance() {
        // Act
        ObjectMapper objectMapper = jacksonConfig.objectMapper();

        // Assert
        assertNotNull(objectMapper, "ObjectMapper no debe ser nulo");
    }

    @Test
    void objectMapper_ShouldSerializeLocalDateTime() throws Exception {
        // Arrange
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        TestClass testObject = new TestClass(dateTime);

        // Act
        String json = objectMapper.writeValueAsString(testObject);

        // Assert
        assertTrue(json.contains("2024-01-01 12:00:00"),
                "La fecha debe ser serializada en el formato esperado");
    }

    @Test
    void objectMapper_ShouldHandleNullLocalDateTime() throws Exception {
        // Arrange
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        TestClass testObject = new TestClass(null);

        // Act
        String json = objectMapper.writeValueAsString(testObject);
        TestClass deserializedObject = objectMapper.readValue(json, TestClass.class);

        // Assert
        assertTrue(json.contains("null"), "La fecha nula debe ser serializada como null");
        assertNull(deserializedObject.getDateTime(), "DateTime debe ser nulo después de deserializar");
    }

    @Test
    void objectMapper_ShouldHandleCustomSerialization() throws Exception {
        // Arrange
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        LocalDateTime now = LocalDateTime.now();
        TestClass testObject = new TestClass(now);

        // Act
        String json = objectMapper.writeValueAsString(testObject);

        // Assert
        String expectedFormat = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertTrue(json.contains(expectedFormat),
                "La fecha debe ser serializada usando el formato personalizado");
    }

    // Clase de prueba auxiliar
    private static class TestClass {
        private LocalDateTime dateTime;

        public TestClass() {
        }

        public TestClass(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }

    @Test
    void objectMapper_ShouldHandleInvalidDateFormat() {
        // Arrange
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        String invalidJson = "{\"dateTime\":\"invalid-date-format\"}";

        // Assert
        assertThrows(Exception.class, () ->
                        objectMapper.readValue(invalidJson, TestClass.class),
                "Debe lanzar una excepción al intentar deserializar un formato de fecha inválido");
    }

    @Test
    void objectMapper_ShouldHaveCustomModule() {
        // Act
        ObjectMapper objectMapper = jacksonConfig.objectMapper();

        // Assert
        boolean hasCustomSerializer = objectMapper.getRegisteredModuleIds().stream()
                .anyMatch(id -> id.toString().contains("SimpleModule"));
        assertTrue(hasCustomSerializer, "Debe tener un módulo personalizado registrado");
    }
}
