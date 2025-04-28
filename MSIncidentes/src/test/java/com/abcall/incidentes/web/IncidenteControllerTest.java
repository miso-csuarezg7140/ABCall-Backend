package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.request.ActualizarIncidenteRequest;
import com.abcall.incidentes.domain.dto.request.CrearIncidenteRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import com.abcall.incidentes.util.ApiUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarReturnsErrorResponseWhenServiceFails() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuario = "invalid";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Mockito.when(incidenteService.consultar(tipoDocUsuario, numeroDocUsuario)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultar(tipoDocUsuario, numeroDocUsuario);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void crearReturnsCreatedResponseWhenValidData() {
        CrearIncidenteRequest crearIncidenteRequest = new CrearIncidenteRequest();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.CREATED.value());

        Mockito.when(incidenteService.crear(crearIncidenteRequest)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.crear(crearIncidenteRequest, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void crearReturnsBadRequestWhenValidationFails() {
        CrearIncidenteRequest crearIncidenteRequest = new CrearIncidenteRequest();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.crear(crearIncidenteRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void pingReturnsPongResponse() {
        ResponseEntity<String> response = incidenteController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }

    @Test
    void consultarDetalleReturnsOkResponseWhenIdIsValid() {
        String idIncidente = "1";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsBadRequestWhenIdIsNull() {
        String idIncidente = null;
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsBadRequestWhenIdIsNotNumeric() {
        String idIncidente = "abc";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsNotFoundWhenIdDoesNotExist() {
        String idIncidente = "999";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.NOT_FOUND.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void actualizarReturnsOkResponseWhenValidData() {
        ActualizarIncidenteRequest actualizarIncidenteRequest = new ActualizarIncidenteRequest();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        Mockito.when(incidenteService.actualizar(actualizarIncidenteRequest)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.actualizar(actualizarIncidenteRequest, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void actualizarReturnsBadRequestWhenValidationFails() {
        ActualizarIncidenteRequest actualizarIncidenteRequest = new ActualizarIncidenteRequest();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.actualizar(actualizarIncidenteRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }
}