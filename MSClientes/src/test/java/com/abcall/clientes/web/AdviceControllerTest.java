package com.abcall.clientes.web;

import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.exception.ApiException;
import com.abcall.clientes.util.ApiUtils;
import com.abcall.clientes.util.enums.HttpResponseCodes;
import com.abcall.clientes.util.enums.HttpResponseMessages;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdviceControllerTest {

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private AdviceController adviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handlerApiExceptionShouldReturnResponseWithExceptionDetails() {
        List<String> additionalMessages = List.of("Additional message");
        ApiException apiException = new ApiException(400, HttpStatus.BAD_REQUEST, "Error message", additionalMessages);

        ResponseServiceDto expectedResponseDto = new ResponseServiceDto(400,
                "Error message", additionalMessages);
        when(apiUtils.buildResponse(400, "Error message", additionalMessages))
                .thenReturn(expectedResponseDto);

        ResponseEntity<ResponseServiceDto> response = adviceController.handlerApiException(apiException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());
    }

    @Test
    void handlerBindExceptionShouldReturnResponseWithValidationError() {
        BindException bindException = mock(BindException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ObjectError objectError = new ObjectError("objectName", "Validation error");

        when(bindException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(objectError));

        ResponseServiceDto expectedResponseDto = new ResponseServiceDto(400,
                "Validation error", List.of());
        when(apiUtils.buildResponse(400, "Validation error", List.of()))
                .thenReturn(expectedResponseDto);

        ResponseEntity<ResponseServiceDto> response = adviceController.handlerBindException(bindException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());
    }

    @Test
    void handlerExceptionShouldReturnResponseWithExceptionDetails() {
        Exception exception = new Exception("Internal server error");
        StackTraceElement[] stackTrace = exception.getStackTrace();

        ResponseServiceDto expectedResponseDto = new ResponseServiceDto(500,
                "Internal server error", stackTrace);
        when(apiUtils.buildResponse(500, "Internal server error", stackTrace))
                .thenReturn(expectedResponseDto);

        ResponseEntity<ResponseServiceDto> response = adviceController.handlerException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());
    }

    @Test
    void handleValidationExceptionsShouldReturnResponseWithValidationErrors() {
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName",
                "Field error message");

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        List<String> errorMessages = List.of("Field error message");

        ResponseServiceDto expectedResponseDto = new ResponseServiceDto(400, "Bad Request",
                errorMessages);

        when(apiUtils.buildResponse(HttpResponseCodes.BAD_REQUEST.getCode(),
                HttpResponseMessages.BAD_REQUEST.getMessage(), errorMessages)).thenReturn(expectedResponseDto);

        ResponseEntity<ResponseServiceDto> response = adviceController.handleValidationExceptions(
                methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());
    }

    @Test
    void handleValidationExceptionsForConstraintViolationsShouldReturnResponseWithValidationErrors() {
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        ConstraintViolation<?> constraintViolation = mock(ConstraintViolation.class);
        when(constraintViolation.getMessage()).thenReturn("Constraint violation message");
        when(constraintViolationException.getConstraintViolations()).thenReturn(Set.of(constraintViolation));

        List<String> errorMessages = List.of("Constraint violation message");

        ResponseServiceDto expectedResponseDto = new ResponseServiceDto(400,
                "Bad Request", errorMessages);

        when(apiUtils.buildResponse(HttpResponseCodes.BAD_REQUEST.getCode(),
                HttpResponseMessages.BAD_REQUEST.getMessage(), errorMessages)).thenReturn(expectedResponseDto);

        ResponseEntity<ResponseServiceDto> response = adviceController.handleValidationExceptions(
                constraintViolationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());
    }
}