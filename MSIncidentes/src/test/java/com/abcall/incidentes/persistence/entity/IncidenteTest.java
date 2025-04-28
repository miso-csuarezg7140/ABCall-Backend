package com.abcall.incidentes.persistence.entity;

import com.abcall.incidentes.domain.dto.request.IncidenteRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.web.IncidenteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncidenteTest {

    @Mock
    private IncidenteService incidenteService;

    @Mock
    private ApiUtils apiUtils;

    @Mock
    private IncidenteController incidenteController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        incidenteController = new IncidenteController(incidenteService, apiUtils);
    }

    @Test
    void crearReturnsInternalServerErrorWhenServiceFails() {
        IncidenteRequest incidenteRequest = new IncidenteRequest();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Mockito.when(incidenteService.crear(incidenteRequest)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.crear(incidenteRequest, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsInternalServerErrorWhenServiceFails() {
        String idIncidente = "1";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarReturnsBadRequestWhenTipoDocUsuarioIsNull() {
        String tipoDocUsuario = null;
        String numeroDocUsuario = "123456";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(incidenteService.consultar(tipoDocUsuario, numeroDocUsuario)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultar(tipoDocUsuario, numeroDocUsuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarReturnsBadRequestWhenNumeroDocUsuarioIsNull() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuario = null;
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.when(incidenteService.consultar(tipoDocUsuario, numeroDocUsuario)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidenteController.consultar(tipoDocUsuario, numeroDocUsuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }
}