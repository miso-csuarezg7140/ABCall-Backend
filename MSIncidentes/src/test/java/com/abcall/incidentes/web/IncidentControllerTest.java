package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IIncidentService;
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
class IncidentControllerTest {

    @Mock
    private IIncidentService incidenteService;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private IncidentController incidentController;

    @Test
    void crearReturnsCreatedResponseWhenValidData() {
        CreateIncidentRequest createIncidentRequest = new CreateIncidentRequest();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.CREATED.value());

        Mockito.when(incidenteService.crear(createIncidentRequest)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.crear(createIncidentRequest, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void crearReturnsBadRequestWhenValidationFails() {
        CreateIncidentRequest createIncidentRequest = new CreateIncidentRequest();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.crear(createIncidentRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void pingReturnsPongResponse() {
        ResponseEntity<String> response = incidentController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }

    @Test
    void consultarDetalleReturnsOkResponseWhenIdIsValid() {
        String idIncidente = "1";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsBadRequestWhenIdIsNull() {
        String idIncidente = null;
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsBadRequestWhenIdIsNotNumeric() {
        String idIncidente = "abc";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsNotFoundWhenIdDoesNotExist() {
        String idIncidente = "999";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.NOT_FOUND.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void actualizarReturnsOkResponseWhenValidData() {
        UpdateIncidentRequest updateIncidentRequest = new UpdateIncidentRequest();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        Mockito.when(incidenteService.actualizar(updateIncidentRequest)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.actualizar(updateIncidentRequest, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void actualizarReturnsBadRequestWhenValidationFails() {
        UpdateIncidentRequest updateIncidentRequest = new UpdateIncidentRequest();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.actualizar(updateIncidentRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }
}