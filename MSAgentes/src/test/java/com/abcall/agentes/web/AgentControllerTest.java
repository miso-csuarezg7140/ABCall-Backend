package com.abcall.agentes.web;

import com.abcall.agentes.domain.dto.request.AgentAuthRequest;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.domain.service.IAgentService;
import com.abcall.agentes.util.ApiUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentControllerTest {

    @Mock
    private IAgentService agentService;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private AgentController agentController;

    @Mock
    private BindingResult bindingResult;

    @Test
    void authenticateWithValidCredentialsReturnsOk() {
        AgentAuthRequest request = new AgentAuthRequest("1", "123456", "password");
        ResponseServiceDto serviceResponse = new ResponseServiceDto();
        serviceResponse.setStatusCode(HttpStatus.OK.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(agentService.authenticate(request.getDocumentType(), request.getDocumentNumber(), request.getPassword()))
                .thenReturn(serviceResponse);

        ResponseEntity<ResponseServiceDto> response = agentController.authenticate(request, bindingResult);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(apiUtils, never()).badRequestResponse(any());
    }

    @Test
    void authenticateWithValidationErrorsReturnsBadRequest() {
        AgentAuthRequest request = new AgentAuthRequest(null, null, null);
        ResponseServiceDto serviceResponse = new ResponseServiceDto();
        serviceResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

        when(bindingResult.hasErrors()).thenReturn(true);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(serviceResponse);

        ResponseEntity<ResponseServiceDto> response = agentController.authenticate(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        verify(agentService, never()).authenticate(any(), any(), any());
    }

    @Test
    void authenticateWithInvalidCredentialsReturnsUnauthorized() {
        AgentAuthRequest request = new AgentAuthRequest("1", "123456", "wrongpass");
        ResponseServiceDto serviceResponse = new ResponseServiceDto();
        serviceResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(agentService.authenticate(request.getDocumentType(), request.getDocumentNumber(), request.getPassword()))
                .thenReturn(serviceResponse);

        ResponseEntity<ResponseServiceDto> response = agentController.authenticate(request, bindingResult);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void documentListWithDataReturnsOk() {
        ResponseServiceDto serviceResponse = new ResponseServiceDto();
        serviceResponse.setStatusCode(HttpStatus.OK.value());

        when(agentService.documentTypeList()).thenReturn(serviceResponse);

        ResponseEntity<ResponseServiceDto> response = agentController.documentList();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void documentListEmptyReturnsNoContent() {
        ResponseServiceDto serviceResponse = new ResponseServiceDto();
        serviceResponse.setStatusCode(HttpStatus.NO_CONTENT.value());

        when(agentService.documentTypeList()).thenReturn(serviceResponse);

        ResponseEntity<ResponseServiceDto> response = agentController.documentList();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
    }

    @Test
    void pingReturnsOkWithPongMessage() {
        ResponseEntity<String> response = agentController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }
}