package com.abcall.clientes.web;

import com.abcall.clientes.domain.dto.request.ClientAuthRequest;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.domain.service.IClientService;
import com.abcall.clientes.util.ApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ClientControllerTest {

    @Mock
    private IClientService clientService;

    @Mock
    private ApiUtils apiUtils;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateClient_ReturnsOkResponse_WhenCredentialsAreValid() {
        ClientAuthRequest request = new ClientAuthRequest("123", "password");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.authenticateClient(request.getUsername(), request.getPassword())).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.authenticateClient(request, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void authenticateClient_ReturnsBadRequestResponse_WhenRequestHasErrors() {
        ClientAuthRequest request = new ClientAuthRequest("123", "password");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        when(bindingResult.hasErrors()).thenReturn(true);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.authenticateClient(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void authenticateClient_ReturnsNotFoundResponse_WhenClientNotFound() {
        ClientAuthRequest request = new ClientAuthRequest("123", "password");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.NOT_FOUND.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.authenticateClient(request.getUsername(), request.getPassword())).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.authenticateClient(request, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void ping_ReturnsPongResponse() {
        ResponseEntity<String> response = clientController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }
}