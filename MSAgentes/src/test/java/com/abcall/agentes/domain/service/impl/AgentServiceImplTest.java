package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.domain.dto.DocumentTypeDto;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import com.abcall.agentes.persistence.repository.IAgentRepository;
import com.abcall.agentes.persistence.repository.IDocumentTypeRepository;
import com.abcall.agentes.util.ApiUtils;
import com.abcall.agentes.util.enums.HttpResponseCodes;
import com.abcall.agentes.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentServiceImplTest {

    private static final String DOCUMENT_TYPE = "1";
    private static final String DOCUMENT_NUMBER = "123456";
    private static final String PASSWORD = "validPassword";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    @Mock
    private IAgentRepository agentRepository;
    @Mock
    private IDocumentTypeRepository documentTypeRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ApiUtils apiUtils;
    @InjectMocks
    private AgentServiceImpl agentService;

    @Test
    void authenticateWithValidCredentialsReturnsOk() {
        AgentDto agentDto = new AgentDto();
        agentDto.setDocumentType(1);
        agentDto.setDocumentNumber(DOCUMENT_NUMBER);
        agentDto.setPassword(ENCODED_PASSWORD);
        agentDto.setNames("John");
        agentDto.setSurnames("Doe");

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.OK.getMessage());

        when(agentRepository.findById(any(AgentPK.class))).thenReturn(agentDto);
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
        when(apiUtils.buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any())).thenReturn(expectedResponse);

        ResponseServiceDto result = agentService.authenticate(DOCUMENT_TYPE, DOCUMENT_NUMBER, PASSWORD);

        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        verify(agentRepository).save(any(AgentDto.class));
    }

    @Test
    void authenticateWithInvalidPasswordReturnsUnauthorized() {
        AgentDto agentDto = new AgentDto();
        agentDto.setPassword(ENCODED_PASSWORD);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());

        when(agentRepository.findById(any(AgentPK.class))).thenReturn(agentDto);
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(false);
        when(apiUtils.buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()),
                eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any())).thenReturn(expectedResponse);

        ResponseServiceDto result = agentService.authenticate(DOCUMENT_TYPE, DOCUMENT_NUMBER, PASSWORD);

        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        verify(agentRepository, never()).save(any(AgentDto.class));
    }

    @Test
    void authenticateWithNonExistentAgentReturnsNoContent() {
        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.BUSINESS_MISTAKE.getCode());

        when(agentRepository.findById(any(AgentPK.class))).thenReturn(null);
        when(apiUtils.buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()), any())).thenReturn(expectedResponse);

        ResponseServiceDto result = agentService.authenticate(DOCUMENT_TYPE, DOCUMENT_NUMBER, PASSWORD);

        assertEquals(HttpResponseCodes.BUSINESS_MISTAKE.getCode(), result.getStatusCode());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticateWithInvalidDocumentTypeReturnsError() {
        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode());

        when(apiUtils.buildResponse(eq(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode()),
                eq(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage()), anyString())).thenReturn(expectedResponse);

        ResponseServiceDto result = agentService.authenticate("invalid", DOCUMENT_NUMBER, PASSWORD);

        assertEquals(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(), result.getStatusCode());
    }

    @Test
    void documentTypeListWithDataReturnsOk() {
        List<DocumentTypeDto> documentTypes = List.of(new DocumentTypeDto());
        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());

        when(documentTypeRepository.getList()).thenReturn(documentTypes);
        when(apiUtils.buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any())).thenReturn(expectedResponse);

        ResponseServiceDto result = agentService.documentTypeList();

        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
    }

    @Test
    void documentTypeListEmptyReturnsNoContent() {
        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.NO_CONTENT.getCode());

        when(documentTypeRepository.getList()).thenReturn(List.of());
        when(apiUtils.buildResponse(eq(HttpResponseCodes.NO_CONTENT.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()), any())).thenReturn(expectedResponse);

        ResponseServiceDto result = agentService.documentTypeList();

        assertEquals(HttpResponseCodes.NO_CONTENT.getCode(), result.getStatusCode());
    }
}