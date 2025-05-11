package com.abcall.incidentes.persistence.entity;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IIncidentService;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.web.IncidentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncidentTest {

    @Mock
    private IIncidentService incidenteService;

    @Mock
    private ApiUtils apiUtils;

    @Mock
    private IncidentController incidentController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        incidentController = new IncidentController(incidenteService, apiUtils);
    }

    @Test
    void crearReturnsInternalServerErrorWhenServiceFails() {
        CreateIncidentRequest createIncidentRequest = new CreateIncidentRequest();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Mockito.when(incidenteService.crear(createIncidentRequest)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.crear(createIncidentRequest, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void consultarDetalleReturnsInternalServerErrorWhenServiceFails() {
        String idIncidente = "1";
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Mockito.when(incidenteService.consultarDetalle(idIncidente)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = incidentController.consultarDetalle(idIncidente);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }
}