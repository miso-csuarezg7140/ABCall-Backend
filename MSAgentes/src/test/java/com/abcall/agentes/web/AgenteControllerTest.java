package com.abcall.agentes.web;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.domain.service.AgenteService;
import com.abcall.agentes.util.enums.HttpResponseCodes;
import com.abcall.agentes.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AgenteControllerTest {

    @Mock
    private AgenteService agenteService;

    @InjectMocks
    private AgenteController agenteController;

    private ResponseServiceDto responseOk;
    private ResponseServiceDto responseUnauthorized;
    private ResponseServiceDto responseNoContent;
    private ResponseServiceDto responseError;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar respuestas de prueba
        // Respuesta exitosa
        responseOk = new ResponseServiceDto();
        responseOk.setStatusCode(HttpResponseCodes.OK.getCode());
        responseOk.setStatusDescription(HttpResponseMessages.OK.getMessage());
        responseOk.setData(new AgenteDto());
        responseOk.setStatusCode(HttpStatus.OK.value());

        // Respuesta no autorizada
        responseUnauthorized = new ResponseServiceDto();
        responseUnauthorized.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        responseUnauthorized.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        responseUnauthorized.setData(new HashMap<>());
        responseUnauthorized.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        // Respuesta sin contenido
        responseNoContent = new ResponseServiceDto();
        responseNoContent.setStatusCode(HttpResponseCodes.BUSINESS_MISTAKE.getCode());
        responseNoContent.setStatusDescription(HttpResponseMessages.NO_CONTENT.getMessage());
        responseNoContent.setData(new HashMap<>());
        responseNoContent.setStatusCode(HttpStatus.NO_CONTENT.value());

        // Respuesta de error
        responseError = new ResponseServiceDto();
        responseError.setStatusCode(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode());
        responseError.setStatusDescription(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage());
        responseError.setData("Error message");
        responseError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void loginSuccess() {
        // Arrange
        String tipoDocumento = "CC";
        String numDocumento = "1010259487";
        String contrasena = "Contrasena123!";

        when(agenteService.login(tipoDocumento, numDocumento, contrasena)).thenReturn(responseOk);

        // Act
        ResponseEntity<ResponseServiceDto> response = agenteController.login(tipoDocumento, numDocumento, contrasena);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(responseOk, response.getBody());

        // Verify
        verify(agenteService).login(tipoDocumento, numDocumento, contrasena);
    }

    @Test
    public void loginUnauthorized() {
        // Arrange
        String tipoDocumento = "CC";
        String numDocumento = "1010259487";
        String contrasena = "ContrasenaMala!";

        when(agenteService.login(tipoDocumento, numDocumento, contrasena)).thenReturn(responseUnauthorized);

        // Act
        ResponseEntity<ResponseServiceDto> response = agenteController.login(tipoDocumento, numDocumento, contrasena);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
        assertEquals(responseUnauthorized, response.getBody());
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), Objects.requireNonNull(response.getBody()).getStatusCode());
        assertEquals(HttpResponseMessages.UNAUTHORIZED.getMessage(), response.getBody().getStatusDescription());

        // Verify
        verify(agenteService).login(tipoDocumento, numDocumento, contrasena);
    }

    @Test
    public void loginNoContent() {
        // Arrange
        String tipoDocumento = "CC";
        String numDocumento = "9999999999"; // Usuario que no existe
        String contrasena = "Contrasena123!";

        when(agenteService.login(tipoDocumento, numDocumento, contrasena)).thenReturn(responseNoContent);

        // Act
        ResponseEntity<ResponseServiceDto> response = agenteController.login(tipoDocumento, numDocumento, contrasena);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
        assertEquals(responseNoContent, response.getBody());
        assertEquals(HttpResponseCodes.NO_CONTENT.getCode(), Objects.requireNonNull(response.getBody()).getStatusCode());
        assertEquals(HttpResponseMessages.NO_CONTENT.getMessage(), response.getBody().getStatusDescription());
        assertInstanceOf(HashMap.class, response.getBody().getData());
        assertTrue(((HashMap<?, ?>) response.getBody().getData()).isEmpty());

        // Verify
        verify(agenteService).login(tipoDocumento, numDocumento, contrasena);
    }

    @Test
    public void loginInternalServerError() {
        // Arrange
        String tipoDocumento = "CC";
        String numDocumento = "1010259487";
        String contrasena = "Contrasena123!";

        when(agenteService.login(tipoDocumento, numDocumento, contrasena)).thenReturn(responseError);

        // Act
        ResponseEntity<ResponseServiceDto> response = agenteController.login(tipoDocumento, numDocumento, contrasena);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals(responseError, response.getBody());
        assertEquals(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(), Objects.requireNonNull(response.getBody()).getStatusCode());
        assertEquals(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), response.getBody().getStatusDescription());
        assertEquals("Error message", response.getBody().getData());

        // Verify
        verify(agenteService).login(tipoDocumento, numDocumento, contrasena);
    }

    @Test
    public void pingTest() {
        // Act
        ResponseEntity<String> response = agenteController.ping();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }
}