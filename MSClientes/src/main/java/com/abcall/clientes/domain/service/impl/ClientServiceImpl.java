package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ClientAuthResponse;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.domain.service.IClientService;
import com.abcall.clientes.persistence.repository.IClienteRepository;
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

    private final IClienteRepository clientRepository;
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
}
