package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.domain.dto.UserClientDtoResponse;
import com.abcall.incidentes.domain.dto.request.ConsultIncidentRequest;
import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.DetailIncidentResponse;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.persistence.repository.IIncidentRepository;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.enums.HttpResponseCodes;
import com.abcall.incidentes.util.enums.HttpResponseMessages;
import com.abcall.incidentes.web.external.IClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentServiceImplTest {

    @Mock
    private IIncidentRepository incidenteRepository;

    @Mock
    private IClientService clientService;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Test
    void consultarReturnsValidResponseWhenAllParametersAreCorrect() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStartDate("2023/01/01");
        request.setEndDate("2023/01/31");
        request.setPage("1");
        request.setPageSize("10");
        request.setClientDocumentNumber("123456789");
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("987654321");
        request.setStatus("ACTIVE");
        request.setDownload(false);

        List<LocalDateTime> dates = List.of(LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 1, 31, 0, 0));
        Page<IncidentResponse> page = new PageImpl<>(List.of(new IncidentResponse()));

        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());
        when(incidenteRepository.getIncidents(any(), any(), any(), any(), any(), any(), anyBoolean(), any()))
                .thenReturn(page);

        ResponseServiceDto response = incidentService.consultar(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()), eq(HttpResponseMessages.OK.getMessage()),
                eq(page.getContent()), any());
    }

    @Test
    void consultarReturnsErrorWhenDatesAreInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStartDate("2023/01/31");
        request.setEndDate("2023/01/01");

        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.consultar(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
    }

    @Test
    void consultarReturnsErrorWhenPaginationParametersAreInvalid() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStartDate("2023/01/01");
        request.setEndDate("2023/01/31");
        request.setPage("1");
        request.setPageSize(null);

        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.consultar(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
    }

    @Test
    void consultarReturnsNoContentWhenNoIncidentsAreFound() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStartDate("2023/01/01");
        request.setEndDate("2023/01/31");
        request.setPage("1");
        request.setPageSize("10");

        List<LocalDateTime> dates = List.of(LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 1, 31, 0, 0));
        Page<IncidentResponse> emptyPage = Page.empty();

        when(incidenteRepository.getIncidents(any(), any(), any(), any(), any(), any(), anyBoolean(), any()))
                .thenReturn(emptyPage);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.consultar(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
    }

    @Test
    void consultarReturnsBase64CsvWhenDownloadIsTrue() {
        ConsultIncidentRequest request = new ConsultIncidentRequest();
        request.setStartDate("2023/01/01");
        request.setEndDate("2023/01/31");
        request.setDownload(true);

        List<IncidentResponse> incidents = List.of(new IncidentResponse());
        Page<IncidentResponse> page = new PageImpl<>(incidents);

        when(incidenteRepository.getIncidents(any(), any(), any(), any(), any(), any(), anyBoolean(), any()))
                .thenReturn(page);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.consultar(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()), eq(HttpResponseMessages.OK.getMessage()),
                anyString());
    }

    @Test
    void consultarDetalleReturnsSuccessWhenIncidentExists() {
        String idIncidente = "1";
        DetailIncidentResponse mockResponse = new DetailIncidentResponse();

        when(incidenteRepository.findById(1)).thenReturn(mockResponse);
        when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockResponse)).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.consultarDetalle(idIncidente);

        assertNotNull(response);
        verify(incidenteRepository).findById(1);
        verify(apiUtils).buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockResponse);
    }

    @Test
    void consultarDetalleReturnsNoContentWhenIncidentNotFound() {
        String idIncidente = "1";

        when(incidenteRepository.findById(1)).thenReturn(null);
        when(apiUtils.buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()), any()))
                .thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.consultarDetalle(idIncidente);

        assertNotNull(response);
        verify(incidenteRepository).findById(1);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
    }

    @Test
    void crearReturnsSuccessWhenUserClientExists() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        UserClientDtoResponse userClientResponse = new UserClientDtoResponse();
        ResponseServiceDto serviceDto = new ResponseServiceDto();
        serviceDto.setData(userClientResponse);
        ResponseServiceDto successResponse = new ResponseServiceDto();

        when(clientService.validateUserClient(any(), any(), any()))
                .thenReturn(ResponseEntity.ok(serviceDto));
        when(incidenteRepository.create(request)).thenReturn(request);
        when(apiUtils.buildResponse(anyInt(), anyString(), any()))
                .thenReturn(successResponse);

        ResponseServiceDto response = incidentService.crear(request);

        assertNotNull(response);
        verify(incidenteRepository).create(request);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.CREATED.getCode()),
                eq(HttpResponseMessages.CREATED.getMessage()), any());
    }

    @Test
    void crearReturnsErrorWhenUserClientNotFound() {
        CreateIncidentRequest request = new CreateIncidentRequest();

        when(clientService.validateUserClient(any(), any(), any()))
                .thenReturn(ResponseEntity.ok(new ResponseServiceDto()));
        when(apiUtils.buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.BUSINESS_MISTAKE.getMessage()), any()))
                .thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.crear(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
    }

    @Test
    void actualizarReturnsSuccessWhenIncidentExists() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        DetailIncidentResponse mockResponse = new DetailIncidentResponse();

        when(incidenteRepository.update(request)).thenReturn(mockResponse);
        when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockResponse)).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.actualizar(request);

        assertNotNull(response);
        verify(incidenteRepository).update(request);
        verify(apiUtils).buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockResponse);
    }

    @Test
    void actualizarReturnsNoContentWhenIncidentNotFound() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();

        when(incidenteRepository.update(request)).thenReturn(null);
        when(apiUtils.buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()), any()))
                .thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = incidentService.actualizar(request);

        assertNotNull(response);
        verify(incidenteRepository).update(request);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
    }

    @Test
    void obtenerUsuarioClienteReturnsNullWhenValidationFails() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        ResponseServiceDto serviceDto = new ResponseServiceDto();
        serviceDto.setData(null);

        when(clientService.validateUserClient(any(), any(), any()))
                .thenReturn(ResponseEntity.ok(serviceDto));

        UserClientDtoResponse response = incidentService.obtenerUsuarioCliente(request);

        assertNull(response);
        verify(clientService).validateUserClient(any(), any(), any());
    }
}