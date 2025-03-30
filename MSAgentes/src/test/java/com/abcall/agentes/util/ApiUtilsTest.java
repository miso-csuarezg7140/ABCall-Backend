package com.abcall.agentes.util;

import com.abcall.agentes.domain.dto.ResponseServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiUtilsTest {

    @Test
    void buildResponseServiceDto_WithValidData() {
        // Arrange
        String statusCode = "200";
        String statusDescription = "Success";
        Object data = new Object();

        // Act
        ResponseServiceDto response = ApiUtils.buildResponseServiceDto(statusCode, statusDescription, data);

        // Assert
        assertNotNull(response);
        assertEquals(statusCode, response.getStatusCode());
        assertEquals(statusDescription, response.getStatusDescription());
        assertEquals(data, response.getData());
    }

    @Test
    void buildResponseServiceDto_WithNullData() {
        // Arrange
        String statusCode = "404";
        String statusDescription = "Not Found";

        // Act
        ResponseServiceDto response = ApiUtils.buildResponseServiceDto(statusCode, statusDescription, null);

        // Assert
        assertNotNull(response);
        assertEquals(statusCode, response.getStatusCode());
        assertEquals(statusDescription, response.getStatusDescription());
        assertNull(response.getData());
    }

    @Test
    void requestHandleErrors_WithValidationErrors() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error1 = new FieldError("object", "field1", "error1");
        FieldError error2 = new FieldError("object", "field2", "error2");

        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(error1, error2));

        // Act
        Map<String, String> errors = ApiUtils.requestHandleErrors(bindingResult);

        // Assert
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertEquals("error1", errors.get("field1"));
        assertEquals("error2", errors.get("field2"));
    }

    @Test
    void requestHandleErrors_ToString() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("object", "field1", "error1");

        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(error));

        // Act
        Map<String, String> errors = ApiUtils.requestHandleErrors(bindingResult);
        String errorString = errors.toString();

        // Assert
        assertTrue(errorString.contains("field1"));
        assertTrue(errorString.contains("error1"));
    }

    @Test
    void encodeToBase64_ValidString() {
        // Arrange
        String originalText = "Hello World!";
        String expectedEncoded = Base64.getEncoder()
                .encodeToString(originalText.getBytes(StandardCharsets.UTF_8));

        // Act
        String encodedText = ApiUtils.encodeToBase64(originalText);

        // Assert
        assertNotNull(encodedText);
        assertEquals(expectedEncoded, encodedText);
        assertTrue(Base64.getDecoder().decode(encodedText.getBytes(StandardCharsets.UTF_8)).length > 0);
    }

    @Test
    void encodeToBase64_EmptyString() {
        // Arrange
        String originalText = "";

        // Act
        String encodedText = ApiUtils.encodeToBase64(originalText);

        // Assert
        assertNotNull(encodedText);
        assertEquals("", encodedText);
    }

    @Test
    void decodeFromBase64_ValidString() {
        // Arrange
        String originalText = "Hello World!";
        String encodedText = Base64.getEncoder()
                .encodeToString(originalText.getBytes(StandardCharsets.UTF_8));

        // Act
        String decodedText = ApiUtils.decodeFromBase64(encodedText);

        // Assert
        assertNotNull(decodedText);
        assertEquals(originalText, decodedText);
    }

    @Test
    void decodeFromBase64_EmptyString() {
        // Arrange
        String encodedText = "";

        // Act
        String decodedText = ApiUtils.decodeFromBase64(encodedText);

        // Assert
        assertNotNull(decodedText);
        assertEquals("", decodedText);
    }

    @Test
    void decodeFromBase64_InvalidBase64() {
        // Arrange
        String invalidBase64 = "This is not base64!";

        // Assert
        assertThrows(RuntimeException.class, () -> ApiUtils.decodeFromBase64(invalidBase64));
    }

    @Test
    void encodeAndDecode_RoundTrip() {
        // Arrange
        String originalText = "Test string with special chars !@#$%^&*()";

        // Act
        String encodedText = ApiUtils.encodeToBase64(originalText);
        String decodedText = ApiUtils.decodeFromBase64(encodedText);

        // Assert
        assertNotNull(encodedText);
        assertNotNull(decodedText);
        assertEquals(originalText, decodedText);
    }

    @Test
    void encodeAndDecode_SpecialCharacters() {
        // Arrange
        String originalText = "áéíóúñÁÉÍÓÚÑ@#$%^&*()";

        // Act
        String encodedText = ApiUtils.encodeToBase64(originalText);
        String decodedText = ApiUtils.decodeFromBase64(encodedText);

        // Assert
        assertNotNull(encodedText);
        assertNotNull(decodedText);
        assertEquals(originalText, decodedText);
    }
}
