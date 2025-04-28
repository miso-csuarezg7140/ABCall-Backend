package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.domain.dto.UserClientDtoResponse;
import com.abcall.incidentes.domain.dto.request.IncidenteRequest;
import com.abcall.incidentes.domain.dto.response.IncidenteDetalleResponse;
import com.abcall.incidentes.domain.dto.response.IncidenteResponse;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import com.abcall.incidentes.persistence.entity.Incidente;
import com.abcall.incidentes.persistence.mappers.IncidenteMapper;
import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.enums.HttpResponseCodes;
import com.abcall.incidentes.util.enums.HttpResponseMessages;
import com.abcall.incidentes.web.external.IClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenteServiceImpl implements IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final IncidenteMapper incidenteMapper;
    private final IClientService clientService;
    private final ApiUtils apiUtils;

    /**
     * Retrieves a list of incidents for a specific user based on their document type and number.
     *
     * @param tipoDocUsuario      the type of the user's document (e.g., "CC" for citizenship card)
     * @param numeroDocUsuarioStr the user's document number as a string
     * @return a {@link ResponseServiceDto} containing the response details, including the list of incidents if found,
     * or an appropriate message if no incidents are found or an error occurs
     */
    @Override
    public ResponseServiceDto consultar(String tipoDocUsuario, String numeroDocUsuarioStr) {
        try {
            Long numeroDocUsuario = Long.valueOf(numeroDocUsuarioStr);
            List<IncidenteResponse> incidenteRequestList = incidenteMapper.toDtoResponseList(
                    incidenteRepository.obtenerPorUsuario(tipoDocUsuario, numeroDocUsuario));

            if (incidenteRequestList != null && !incidenteRequestList.isEmpty()) {
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                        incidenteRequestList);
            } else {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
            }
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Retrieves the details of a specific incident based on its ID.
     *
     * @param idIncidenteStr the ID of the incident as a string
     * @return a {@link ResponseServiceDto} containing the response details, including the incident details if found,
     * or an appropriate message if the incident is not found or an error occurs
     */
    @Override
    public ResponseServiceDto consultarDetalle(String idIncidenteStr) {
        try {
            Integer idIncidente = Integer.valueOf(idIncidenteStr);
            IncidenteDetalleResponse incidenteDetalleResponse = incidenteMapper.toDtoDetalleResponse(
                    incidenteRepository.obtenerPorId(idIncidente));

            if (incidenteDetalleResponse != null) {
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                        incidenteDetalleResponse);
            } else {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
            }
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Creates a new incident based on the provided incident request.
     *
     * @param incidenteRequest the incident request containing the details of the incident to be created
     * @return a {@link ResponseServiceDto} containing the response details, including the created incident or error information
     */
    @Override
    public ResponseServiceDto crear(IncidenteRequest incidenteRequest) {
        try {

            UserClientDtoResponse userClientDtoResponse = obtenerUsuarioCliente(incidenteRequest);
            if (null == userClientDtoResponse) {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
            }

            Incidente incidente = incidenteMapper.toEntity(incidenteRequest);
            IncidenteRequest incidenteCreado = incidenteMapper.toDto(incidenteRepository.crear(incidente));
            return apiUtils.buildResponse(HttpResponseCodes.CREATED.getCode(),
                    HttpResponseMessages.CREATED.getMessage(), incidenteCreado);
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Retrieves the user client information based on the provided incident request.
     *
     * @param incidenteRequest the incident request containing the client and user document details
     * @return a {@link UserClientDtoResponse} object containing the user client data
     * @throws NullPointerException if the response body or its data is null
     */
    private UserClientDtoResponse obtenerUsuarioCliente(IncidenteRequest incidenteRequest) {
        ResponseEntity<ResponseServiceDto> response = clientService.validateUserClient(
                incidenteRequest.getNumDocumentoCliente(), incidenteRequest.getTipoDocumentoUsuario(),
                incidenteRequest.getNumDocumentoUsuario());

        if (response != null && response.getBody() != null && response.getBody().getData() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules();
                String jsonStr = mapper.writeValueAsString(response.getBody().getData());
                return mapper.readValue(jsonStr, UserClientDtoResponse.class);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
