package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.request.ConsultIncidentRequest;
import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IIncidentService;
import com.abcall.incidentes.util.ApiUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentControllerTest {

    @Mock
    private IIncidentService incidentesService;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private IncidentController incidentController;

    @Test
    void consultarReturnsSuccessfulResponseWhenRequestIsValid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        ResponseServiceDto response = new ResponseServiceDto(200, "Consulta exitosa.",
                new Object());
        when(incidentesService.consultar(request)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.consultar(request,
                new BeanPropertyBindingResult(request, "request"));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void consultarReturnsBadRequestWhenBindingResultHasErrors() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        bindingResult.reject("error");
        ResponseServiceDto response = new ResponseServiceDto(400,
                "Valores nulos o incorrectos en los parámetros de entrada.", null);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.consultar(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void crearReturnsSuccessfulResponseWhenRequestIsValid() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        ResponseServiceDto response = new ResponseServiceDto(200, "Creación exitosa.",
                new Object());
        when(incidentesService.crear(request)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.crear(request,
                new BeanPropertyBindingResult(request, "request"));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void crearReturnsBadRequestWhenBindingResultHasErrors() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        bindingResult.reject("error");
        ResponseServiceDto response = new ResponseServiceDto(400,
                "Valores nulos o incorrectos en los parámetros de entrada.", null);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.crear(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void consultarDetalleReturnsSuccessfulResponseWhenIdIsValid() {
        String idIncidente = "1";
        ResponseServiceDto response = new ResponseServiceDto(200, "Consulta exitosa.",
                new Object());
        when(incidentesService.consultarDetalle(idIncidente)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void actualizarReturnsSuccessfulResponseWhenRequestIsValid() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        ResponseServiceDto response = new ResponseServiceDto(200, "Actualización exitosa.",
                new Object());
        when(incidentesService.actualizar(request)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.actualizar(request,
                new BeanPropertyBindingResult(request, "request"));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void actualizarReturnsBadRequestWhenBindingResultHasErrors() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        bindingResult.reject("error");
        ResponseServiceDto response = new ResponseServiceDto(400,
                "Valores nulos o incorrectos en los parámetros de entrada.", null);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(response);

        ResponseEntity<ResponseServiceDto> result = incidentController.actualizar(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void pingReturnsPongResponse() {
        ResponseEntity<String> result = incidentController.ping();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("pong", result.getBody());
    }
}