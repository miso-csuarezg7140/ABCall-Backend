package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.domain.dto.request.IncidenteRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.persistence.entity.Incidente;
import com.abcall.incidentes.persistence.mappers.IncidenteMapper;
import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.enums.HttpResponseCodes;
import com.abcall.incidentes.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class IncidenteServiceImplTest {

    @Mock
    private IncidenteRepository incidenteRepository;

    @Mock
    private IncidenteMapper incidenteMapper;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private IncidenteServiceImpl incidenteService;

    @Test
    void consultarReturnsIncidentesWhenDataExists() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuarioStr = "123456";
        Long numeroDocUsuario = 123456L;
        List<Incidente> incidentes = List.of(new Incidente());
        List<IncidenteRequest> incidenteRequests = List.of(new IncidenteRequest());
        ResponseServiceDto expectedResponse = new ResponseServiceDto();

        Mockito.when(incidenteRepository.obtenerPorUsuario(tipoDocUsuario, numeroDocUsuario)).thenReturn(incidentes);
        Mockito.when(incidenteMapper.toDtoList(incidentes)).thenReturn(incidenteRequests);
        Mockito.when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                        incidenteRequests))
                .thenReturn(expectedResponse);

        ResponseServiceDto actualResponse = incidenteService.consultar(tipoDocUsuario, numeroDocUsuarioStr);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void consultarReturnsNoContentWhenNoDataExists() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuarioStr = "123456";
        Long numeroDocUsuario = 123456L;
        List<Incidente> incidentes = List.of();
        ResponseServiceDto expectedResponse = new ResponseServiceDto();

        Mockito.when(incidenteRepository.obtenerPorUsuario(tipoDocUsuario, numeroDocUsuario)).thenReturn(incidentes);
        Mockito.when(incidenteMapper.toDtoList(incidentes)).thenReturn(List.of());
        Mockito.when(apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>()))
                .thenReturn(expectedResponse);

        ResponseServiceDto actualResponse = incidenteService.consultar(tipoDocUsuario, numeroDocUsuarioStr);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void consultarHandlesExceptionGracefully() {
        String tipoDocUsuario = "CC";
        String numeroDocUsuarioStr = "invalid";
        ResponseServiceDto expectedResponse = new ResponseServiceDto();

        Mockito.when(apiUtils.buildResponse(eq(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode()),
                        eq(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage()), Mockito.anyString()))
                .thenReturn(expectedResponse);

        ResponseServiceDto actualResponse = incidenteService.consultar(tipoDocUsuario, numeroDocUsuarioStr);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void crearReturnsCreatedResponseWhenSuccessful() {
        IncidenteRequest incidenteRequest = new IncidenteRequest();
        Incidente incidente = new Incidente();
        IncidenteRequest incidenteCreado = new IncidenteRequest();
        ResponseServiceDto expectedResponse = new ResponseServiceDto();

        Mockito.when(incidenteMapper.toEntity(incidenteRequest)).thenReturn(incidente);
        Mockito.when(incidenteRepository.crear(incidente)).thenReturn(incidente);
        Mockito.when(incidenteMapper.toDto(incidente)).thenReturn(incidenteCreado);
        Mockito.when(apiUtils.buildResponse(HttpResponseCodes.CREATED.getCode(),
                        HttpResponseMessages.CREATED.getMessage(), incidenteCreado))
                .thenReturn(expectedResponse);

        ResponseServiceDto actualResponse = incidenteService.crear(incidenteRequest);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void crearHandlesExceptionGracefully() {
        IncidenteRequest incidenteRequest = new IncidenteRequest();
        ResponseServiceDto expectedResponse = new ResponseServiceDto();

        Mockito.when(incidenteMapper.toEntity(incidenteRequest)).thenThrow(new RuntimeException("Error"));
        Mockito.when(apiUtils.buildResponse(eq(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode()),
                        eq(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage()), Mockito.anyString()))
                .thenReturn(expectedResponse);

        ResponseServiceDto actualResponse = incidenteService.crear(incidenteRequest);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}