package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.domain.dto.PaginationDto;
import com.abcall.incidentes.domain.dto.UserClientDtoResponse;
import com.abcall.incidentes.domain.dto.request.ConsultIncidentRequest;
import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.DetailIncidentResponse;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IIncidentService;
import com.abcall.incidentes.persistence.repository.IIncidentRepository;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.enums.HttpResponseCodes;
import com.abcall.incidentes.util.enums.HttpResponseMessages;
import com.abcall.incidentes.web.external.IClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IIncidentService {

    private final IIncidentRepository incidenteRepository;
    private final IClientService clientService;
    private final ApiUtils apiUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles the consultation of incidents based on the provided request parameters.
     * <p>
     * This method validates the date range and pagination parameters, retrieves the incidents
     * from the repository, and returns the results in a paginated format. If the `download` flag
     * is set, the incidents are converted to a Base64-encoded CSV string.
     *
     * @param consultIncidentRequest the request object containing the parameters for the consultation, including:
     *                               - startDate: the start date of the consultation period (format: "yyyy/MM/dd")
     *                               - endDate: the end date of the consultation period (format: "yyyy/MM/dd")
     *                               - page: the page number for pagination (optional)
     *                               - pageSize: the size of the page for pagination (optional)
     *                               - clientDocumentNumber: the client document number (optional)
     *                               - userDocumentType: the user document type (optional)
     *                               - userDocumentNumber: the user document number (optional)
     *                               - status: the state of the incidents to filter by (optional)
     *                               - download: a flag indicating whether to download the results as a CSV (optional)
     * @return a {@link ResponseServiceDto} containing the response details, including a list of incidents if found,
     * a Base64-encoded CSV string if `download` is true, or an appropriate message if no incidents are found
     * @throws Exception if an error occurs during the process
     */
    @Override
    public ResponseServiceDto consultar(ConsultIncidentRequest consultIncidentRequest) {
        try {
            List<LocalDateTime> datesValidation = validarFechas(
                    consultIncidentRequest.getStartDate(), consultIncidentRequest.getEndDate());

            if (null == datesValidation)
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());

            boolean pageValidation = validarParametrosPaginacion(
                    consultIncidentRequest.getPage(), consultIncidentRequest.getPageSize());

            if (!pageValidation)
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());

            Pageable pageable = Pageable.unpaged();

            if (null != consultIncidentRequest.getPage()) {
                Sort sort = Sort.by(Sort.Order.asc("status"), Sort.Order.desc("createdDate"));
                pageable = PageRequest.of(Integer.parseInt(consultIncidentRequest.getPage()) - 1,
                        Integer.parseInt(consultIncidentRequest.getPageSize()), sort);
            }

            Long numeroDocumentoCliente = null != consultIncidentRequest.getClientDocumentNumber()
                    ? Long.valueOf(consultIncidentRequest.getClientDocumentNumber()) : null;
            Integer tipoDocumentoUsuario = null != consultIncidentRequest.getUserDocumentType()
                    ? Integer.valueOf(consultIncidentRequest.getUserDocumentType()) : null;

            Page<IncidentResponse> incidenteRequestPage;

            if (!datesValidation.isEmpty())
                incidenteRequestPage = incidenteRepository.getIncidents(numeroDocumentoCliente, tipoDocumentoUsuario,
                        consultIncidentRequest.getUserDocumentNumber(), consultIncidentRequest.getStatus(),
                        datesValidation.getFirst(), datesValidation.get(1), true, pageable);
            else
                incidenteRequestPage = incidenteRepository.getIncidents(numeroDocumentoCliente, tipoDocumentoUsuario,
                        consultIncidentRequest.getUserDocumentNumber(), consultIncidentRequest.getStatus(),
                        null, null, false, pageable);

            PaginationDto paginationDto = new PaginationDto(null != consultIncidentRequest.getPage()
                    ? Integer.parseInt(consultIncidentRequest.getPage()) : 1, incidenteRequestPage.getSize(),
                    incidenteRequestPage.getTotalPages(), incidenteRequestPage.getTotalElements());

            if (!incidenteRequestPage.getContent().isEmpty()) {
                if (null == consultIncidentRequest.getPage() && consultIncidentRequest.getDownload()) {
                    objectMapper.registerModule(new JavaTimeModule());
                    String jsonString = objectMapper.writeValueAsString(incidenteRequestPage.getContent());
                    List<Map<String, Object>> jsonData = objectMapper.readValue(jsonString,
                            objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
                    return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                            convertJsonToCsvBase64(jsonData));
                } else
                    return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                            incidenteRequestPage.getContent(), paginationDto);
            } else
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Validates whether the start date is earlier than or equal to the end date.
     *
     * @param startDate the start date as a string in the format "yyyy/MM/dd"
     * @param endDate   the end date as a string in the format "yyyy/MM/dd"
     * @return {@code true} if the start date is earlier than or equal to the end date, {@code false} otherwise
     * @throws DateTimeParseException if the provided dates cannot be parsed into the expected format
     */
    public List<LocalDateTime> validarFechas(String startDate, String endDate) {
        try {
            List<LocalDateTime> dates = new ArrayList<>();
            if (null == startDate && null == endDate)
                return dates;
            else if (null == startDate || null == endDate)
                return null;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            LocalDate fechaInicioDate = LocalDate.parse(startDate, formatter);
            LocalDate fechaFinDate = LocalDate.parse(endDate, formatter);

            LocalDateTime fechaInicioDateTime = fechaInicioDate.atStartOfDay();
            LocalDateTime fechaFinDateTime = fechaFinDate.atStartOfDay();

            boolean fechasValidas = !fechaInicioDateTime.isAfter(fechaFinDateTime);

            if (fechasValidas) {
                dates.add(fechaInicioDateTime);
                dates.add(fechaFinDateTime);
                return dates;
            } else {
                return null;
            }
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public boolean validarParametrosPaginacion(String pagina, String tamanioPagina) {
        return (pagina == null && tamanioPagina == null) || (pagina != null && tamanioPagina != null);
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
            DetailIncidentResponse detailIncidentResponse = incidenteRepository.findById(idIncidente);

            if (detailIncidentResponse != null)
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                        detailIncidentResponse);
            else
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Creates a new incident based on the provided incident request.
     *
     * @param createIncidentRequest the incident request containing the details of the incident to be created
     * @return a {@link ResponseServiceDto} containing the response details, including the created incident or error information
     */
    @Override
    public ResponseServiceDto crear(CreateIncidentRequest createIncidentRequest) {
        try {
            UserClientDtoResponse userClientDtoResponse = obtenerUsuarioCliente(createIncidentRequest);
            if (null == userClientDtoResponse)
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());

            CreateIncidentRequest incidenteCreado = incidenteRepository.create(createIncidentRequest);
            return apiUtils.buildResponse(HttpResponseCodes.CREATED.getCode(),
                    HttpResponseMessages.CREATED.getMessage(), incidenteCreado);
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Updates an existing incident based on the provided update request.
     *
     * @param updateIncidentRequest the request object containing the details of the incident to be updated
     * @return a {@link ResponseServiceDto} containing the response details, including the updated incident details if successful,
     * or an appropriate message if the update fails or an error occurs
     */
    @Override
    public ResponseServiceDto actualizar(UpdateIncidentRequest updateIncidentRequest) {
        try {
            DetailIncidentResponse detailIncidentResponse = incidenteRepository.update(
                    updateIncidentRequest);

            if (null != detailIncidentResponse)
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), detailIncidentResponse);
            else
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Retrieves the user client information based on the provided incident request.
     *
     * @param createIncidentRequest the incident request containing the client and user document details
     * @return a {@link UserClientDtoResponse} object containing the user client data
     * @throws NullPointerException if the response body or its data is null
     */
    UserClientDtoResponse obtenerUsuarioCliente(CreateIncidentRequest createIncidentRequest) {
        ResponseEntity<ResponseServiceDto> response = clientService.validateUserClient(
                createIncidentRequest.getClientDocumentNumber(), createIncidentRequest.getUserDocumentType(),
                createIncidentRequest.getUserDocumentNumber());

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

    /**
     * Converts a list of JSON objects into a CSV string and encodes it in Base64 format.
     * <p>
     * This method takes a list of maps representing JSON data, converts it into a CSV format,
     * and then encodes the resulting CSV string into Base64. If the input list is empty,
     * the method returns an empty Base64 string. Special characters in the data are escaped
     * to ensure CSV compatibility.
     *
     * @param jsonData a list of maps where each map represents a JSON object with key-value pairs
     * @return a Base64-encoded string representing the CSV data
     * @throws RuntimeException if an error occurs during the conversion or encoding process
     */
    public String convertJsonToCsvBase64(List<Map<String, Object>> jsonData) {
        try {
            StringBuilder csv = new StringBuilder();

            if (!jsonData.isEmpty()) {
                Map<String, Object> firstItem = jsonData.getFirst();
                csv.append(String.join(",", firstItem.keySet()));
                csv.append("\n");

                for (Map<String, Object> item : jsonData) {
                    csv.append(item.values().stream()
                            .map(this::escapeSpecialCharacters)
                            .collect(Collectors.joining(",")));
                    csv.append("\n");
                }
            }

            byte[] csvBytes = csv.toString().getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(csvBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error al convertir JSON a CSV Base64", e);
        }
    }

    /**
     * Escapes special characters in a given value to make it CSV-compatible.
     * <p>
     * This method ensures that values containing commas, double quotes, or newlines
     * are properly escaped to conform to CSV formatting rules. If the value is null,
     * an empty string is returned.
     *
     * @param value the object to be converted to a CSV-compatible string
     * @return a string with special characters escaped for CSV formatting
     */
    private String escapeSpecialCharacters(Object value) {
        if (value == null) {
            return "";
        }
        String result = value.toString();
        // Escapar comillas y caracteres especiales para CSV
        if (result.contains(",") || result.contains("\"") || result.contains("\n")) {
            result = "\"" + result.replace("\"", "\"\"") + "\"";
        }
        return result;
    }
}
