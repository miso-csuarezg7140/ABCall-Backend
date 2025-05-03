package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ClientAuthResponse;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.domain.service.IClientService;
import com.abcall.clientes.persistence.repository.IClientRepository;
import com.abcall.clientes.persistence.repository.IUserClientRepository;
import com.abcall.clientes.util.ApiUtils;
import com.abcall.clientes.util.Constants;
import com.abcall.clientes.util.enums.HttpResponseCodes;
import com.abcall.clientes.util.enums.HttpResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IUserClientRepository userClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiUtils apiUtils;

    /**
     * Authenticates a client based on the provided username and password.
     *
     * @param username the username of the client
     * @param password the password of the client
     * @return a ResponseServiceDto containing the authentication result
     */
    @Override
    public ResponseServiceDto authenticateClient(String username, String password) {
        try {
            ClientDto clientDto = clientRepository.findByDocumentNumber(Long.parseLong(username));

            if (null == clientDto) {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
            }

            boolean isPasswordValid = passwordEncoder.matches(password, clientDto.getPassword());

            if (!password.equals(clientDto.getPassword())) {
                return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                        HttpResponseMessages.UNAUTHORIZED.getMessage(), new HashMap<>());
            }

            clientDto.setLastLogin(LocalDateTime.now());
            clientRepository.save(clientDto);

            ClientAuthResponse clientAuthResponse = ClientAuthResponse.builder()
                    .clientId(clientDto.getIdClient())
                    .documentNumber(clientDto.getDocumentNumber())
                    .authenticated(true)
                    .roles(List.of("cliente"))
                    .socialReason(clientDto.getSocialReason())
                    .email(clientDto.getEmail())
                    .lastLogin(clientDto.getLastLogin())
                    .build();

            return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                    HttpResponseMessages.OK.getMessage(), clientAuthResponse);

        } catch (Exception e) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
        }
    }


    /**
     * Registra un nuevo cliente en el sistema con contraseña codificada.
     *
     * @param clientRegisterRequest DTO con los datos del cliente a registrar
     * @return ResponseServiceDto con el resultado del registro
     */
    @Override
    public ResponseServiceDto registerClient(ClientRegisterRequest clientRegisterRequest) {
        try {
            ClientDto existingClient = clientRepository.findByDocumentNumber(
                    Long.parseLong(clientRegisterRequest.getDocumentNumber()));

            if (existingClient != null) {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
            }

            String encodedPassword = passwordEncoder.encode(clientRegisterRequest.getPassword());

            ClientDto newClient = ClientDto.builder()
                    .documentNumber(Long.parseLong(clientRegisterRequest.getDocumentNumber()))
                    .socialReason(clientRegisterRequest.getSocialReason())
                    .email(clientRegisterRequest.getEmail())
                    .password(clientRegisterRequest.getPassword())
                    .createdDate(Constants.HOY)
                    .status(Constants.ESTADO_DEFAULT)
                    .build();

            ClientDto savedClient = clientRepository.save(newClient);

            Map<String, Object> response = new HashMap<>();
            response.put("clientId", savedClient.getIdClient());
            response.put("documentNumber", savedClient.getDocumentNumber());
            response.put("email", savedClient.getEmail());

            return apiUtils.buildResponse(HttpResponseCodes.CREATED.getCode(),
                    HttpResponseMessages.CREATED.getMessage(), response);

        } catch (Exception e) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
        }
    }

    /**
     * Validates the association between a client and a user based on their document numbers and document type.
     *
     * @param documentClientStr the document number of the client as a string
     * @param documentTypeUser  the document type of the user
     * @param documentUserStr   the document number of the user as a string
     * @return a ResponseServiceDto containing the validation result:
     * - If the client is not found, returns a BUSINESS_MISTAKE response.
     * - If the user-client association is found, returns an OK response with the associated data.
     * - If the user-client association is not found, returns a BUSINESS_MISTAKE response.
     * - If an exception occurs, returns an INTERNAL_SERVER_ERROR response with the exception message.
     */
    @Override
    public ResponseServiceDto validateUserClient(
            String documentClientStr, String documentTypeUser, String documentUserStr) {
        try {
            Long documentClient = Long.parseLong(documentClientStr);
            Long documentUser = Long.parseLong(documentUserStr);

            ClientDto clientDto = clientRepository.findByDocumentNumber(documentClient);

            if (clientDto == null) {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
            }

            UserClientDto userClientDto = new UserClientDto(documentTypeUser, documentUser, clientDto.getIdClient());
            UserClientDto userClientDtoFound = userClientRepository.findById(userClientDto);

            if (null != userClientDtoFound)
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), userClientDtoFound);
            else
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Retrieves a list of active clients from the repository and builds a response.
     *
     * @return ResponseServiceDto containing:
     * - An OK response with the list of active clients if found.
     * - A NO_CONTENT response if no active clients are found.
     * - An INTERNAL_SERVER_ERROR response if an exception occurs.
     */
    @Override
    public ResponseServiceDto listarClientes() {
        try {
            List<ListClientResponse> clientDtoList = clientRepository.findActiveClients();

            if (clientDtoList.isEmpty()) {
                return apiUtils.buildResponse(HttpResponseCodes.NO_CONTENT.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
            } else {
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), clientDtoList);
            }
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }
}
