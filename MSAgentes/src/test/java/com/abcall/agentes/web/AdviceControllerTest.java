package com.abcall.agentes.web;

import com.abcall.agentes.domain.dto.ResponseServiceDto;
import com.abcall.agentes.exception.ApiException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.abcall.agentes.util.Constant.CODIGO_400;
import static com.abcall.agentes.util.Constant.MENSAJE_400;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdviceControllerTest {

    @InjectMocks
    private AdviceController adviceController;

    @Test
    void handlerApiException() {
        // Arrange
        String errorMessage = "Error de API";
        String errorCode = "400";
        String additionalInfo = "Información adicional";
        ApiException apiException = new ApiException(errorCode, HttpStatus.BAD_REQUEST, additionalInfo,
                List.of(errorMessage));

        // Act
        ResponseEntity<ResponseServiceDto> response = adviceController.handlerApiException(apiException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorCode, response.getBody().getStatusCode());
        assertEquals(List.of(errorMessage), response.getBody().getData());
        assertEquals(additionalInfo, response.getBody().getStatusDescription());
    }

    @Test
    void handlerBindException() {
        // Arrange
        String errorMessage = "Campo requerido";
        BindException bindException = mock(BindException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError objectError = new ObjectError("objectName", errorMessage);

        when(bindException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(objectError));

        // Act
        ResponseEntity<ResponseServiceDto> response = adviceController.handlerBindException(bindException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getStatusCode());
        assertEquals(errorMessage, response.getBody().getStatusDescription());
        assertTrue(((List<?>) response.getBody().getData()).isEmpty());
    }

    @Test
    void handlerException() {
        // Arrange
        String errorMessage = "Error general";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<ResponseServiceDto> response = adviceController.handlerException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("500", response.getBody().getStatusCode());
        assertEquals(errorMessage, response.getBody().getStatusDescription());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        String errorMessage = "Campo inválido";
        FieldError fieldError = new FieldError("objectName", "fieldName", errorMessage);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<ResponseServiceDto> response = adviceController.handleValidationExcpetions(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(CODIGO_400, response.getBody().getStatusCode());
        assertEquals(MENSAJE_400, response.getBody().getStatusDescription());
        assertTrue(((List<?>) response.getBody().getData()).contains(errorMessage));
    }

    @Test
    void handleConstraintViolationException() {
        // Arrange
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        String errorMessage = "Violación de restricción";

        when(violation.getMessage()).thenReturn(errorMessage);
        violations.add(violation);

        ConstraintViolationException ex = new ConstraintViolationException("Error de validación", violations);

        // Act
        ResponseEntity<ResponseServiceDto> response = adviceController.handleValidationExcpetions(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(CODIGO_400, response.getBody().getStatusCode());
        assertEquals(MENSAJE_400, response.getBody().getStatusDescription());
        assertTrue(((List<?>) response.getBody().getData()).contains(errorMessage));
    }

    @Test
    void handleValidationExceptionsWithUnknownException() {
        // Arrange
        Exception unknownException = new Exception("Error desconocido");

        // Act
        ResponseEntity<ResponseServiceDto> response = adviceController.handleValidationExcpetions(unknownException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(CODIGO_400, response.getBody().getStatusCode());
        assertEquals(MENSAJE_400, response.getBody().getStatusDescription());
        assertTrue(((List<?>) response.getBody().getData()).isEmpty());
    }
}