package com.abcall.clientes.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomLocalDateTimeSerializerTest {

    @InjectMocks
    private CustomLocalDateTimeSerializer serializer;

    @Test
    void serialize_WritesFormattedDateTime_WhenLocalDateTimeIsNotNull() throws IOException {
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 15, 10, 30);
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        SerializerProvider serializerProvider = mock(SerializerProvider.class);

        serializer.serialize(dateTime, jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeString("2024-03-15 10:30:00");
    }

    @Test
    void serialize_WritesNull_WhenLocalDateTimeIsNull() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        SerializerProvider serializerProvider = mock(SerializerProvider.class);

        serializer.serialize(null, jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeNull();
    }
}