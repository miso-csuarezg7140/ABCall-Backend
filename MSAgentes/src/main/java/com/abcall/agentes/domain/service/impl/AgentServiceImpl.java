package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.domain.dto.DocumentTypeDto;
import com.abcall.agentes.domain.dto.response.AgentAuthResponse;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.domain.service.IAgentService;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import com.abcall.agentes.persistence.repository.IAgentRepository;
import com.abcall.agentes.persistence.repository.IDocumentTypeRepository;
import com.abcall.agentes.util.ApiUtils;
import com.abcall.agentes.util.enums.HttpResponseCodes;
import com.abcall.agentes.util.enums.HttpResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements IAgentService {

    private final IAgentRepository agentRepository;
    private final IDocumentTypeRepository documentTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiUtils apiUtils;

    /**
     * Authenticates an agent based on their document type, document number, and password.
     *
     * @param documentType   The type of document associated with the agent (e.g., ID type).
     * @param documentNumber The document number of the agent.
     * @param password       The password provided for authentication.
     * @return A ResponseServiceDto containing the result of the authentication process:
     * - If the agent is found and the password is valid, returns a response with status OK and agent details.
     * - If the agent is found but the password is invalid, returns a response with status UNAUTHORIZED.
     * - If the agent is not found, returns a response with status BUSINESS_MISTAKE and no content.
     * - If an exception occurs, returns a response with status INTERNAL_SERVER_ERROR and the exception message.
     */
    @Override
    public ResponseServiceDto authenticate(String documentType, String documentNumber, String password) {
        try {
            AgentPK agentPK = new AgentPK();
            agentPK.setDocumentType(Integer.parseInt(documentType));
            agentPK.setDocumentNumber(documentNumber);
            AgentDto agentDto = agentRepository.findById(agentPK);

            if (null != agentDto) {
                boolean isPasswordValid = passwordEncoder.matches(password, agentDto.getPassword());

                if (!isPasswordValid)
                    return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                            HttpResponseMessages.UNAUTHORIZED.getMessage(), new HashMap<>());

                agentDto.setLastLogin(LocalDateTime.now());
                agentRepository.save(agentDto);

                AgentAuthResponse agentAuthResponse = AgentAuthResponse.builder()
                        .documentType(agentDto.getDocumentType())
                        .documentNumber(agentDto.getDocumentNumber())
                        .authenticated(true)
                        .roles(List.of("agente"))
                        .names(agentDto.getNames())
                        .surnames(agentDto.getSurnames())
                        .build();

                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), agentAuthResponse);
            } else
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Retrieves a list of document types from the repository and builds a response.
     *
     * @return ResponseServiceDto containing:
     * - An OK response with the list of document types if found.
     * - A NO_CONTENT response if no document types are found.
     * - An INTERNAL_SERVER_ERROR response if an exception occurs.
     */
    @Override
    public ResponseServiceDto documentTypeList() {
        try {
            List<DocumentTypeDto> documentTypeDtoList = documentTypeRepository.getList();

            if (null != documentTypeDtoList && !documentTypeDtoList.isEmpty())
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), documentTypeDtoList);
            else
                return apiUtils.buildResponse(HttpResponseCodes.NO_CONTENT.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }
}
