package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import com.abcall.incidentes.util.ApiUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@ExtendWith(MockitoExtension.class)
class IncidenteControllerTest {

    @Mock
    private IncidenteService incidenteService;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private IncidenteController incidenteController;

    @Test
    void consultarReturnsOkResponseWhenDataExists() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuario = "123456";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        Mockito.when(incidenteService.consultar(tipoDocUsuario, numeroDocUsuario)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultar(tipoDocUsuario, numeroDocUsuario);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarReturnsErrorResponseWhenServiceFails() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuario = "invalid";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Mockito.when(incidenteService.consultar(tipoDocUsuario, numeroDocUsuario)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultar(tipoDocUsuario, numeroDocUsuario);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void crearReturnsCreatedResponseWhenValidData() {
        IncidenteDto incidenteDto = new IncidenteDto();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.CREATED.value());

        Mockito.when(incidenteService.crear(incidenteDto)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.crear(incidenteDto, Mockito.mock(BindingResult.class));

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void crearReturnsBadRequestWhenValidationFails() {
        IncidenteDto incidenteDto = new IncidenteDto();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.crear(incidenteDto, bindingResult);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void pingReturnsPongResponse() {
        ResponseEntity<String> response = incidenteController.ping();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("pong", response.getBody());
    }
}