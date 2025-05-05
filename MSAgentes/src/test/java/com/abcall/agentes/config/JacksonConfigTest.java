package com.abcall.agentes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JacksonConfigTest {

    @InjectMocks
    private JacksonConfig jacksonConfig;

    @Test
    void objectMapper_SerializesAndDeserializesLocalDateTime() throws Exception {
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 15, 10, 30);

        String json = objectMapper.writeValueAsString(dateTime);

        assertNotNull(objectMapper);
        assertNotNull(json);
        assertTrue(json.contains("2024"));
        assertTrue(json.contains("03"));
        assertTrue(json.contains("15"));
    }

    @Test
    void objectMapper_SerializesLocalDateTimeAsISOFormat() throws Exception {
        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 15, 10, 30);

        String json = objectMapper.writeValueAsString(dateTime);
        String expected = "\"2024-03-15 10:30:00\"";

        assertEquals(expected, json);
    }
}