package com.abcall.clientes.web;

import com.abcall.clientes.domain.dto.request.ClientAuthRequest;
import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
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
import static org.mockito.Mockito.verify;
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
    void authenticate_ReturnsOkResponse_WhenCredentialsAreValid() {
        ClientAuthRequest request = new ClientAuthRequest("123", "password");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.authenticate(request.getDocumentNumber(), request.getPassword())).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.authenticate(request, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void authenticate_ReturnsBadRequestResponse_WhenRequestHasErrors() {
        ClientAuthRequest request = new ClientAuthRequest("123", "password");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        when(bindingResult.hasErrors()).thenReturn(true);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.authenticate(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void authenticateClient_ReturnsNotFoundResponse_WhenNotFound() {
        ClientAuthRequest request = new ClientAuthRequest("123", "password");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.NOT_FOUND.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.authenticate(request.getDocumentNumber(), request.getPassword())).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.authenticate(request, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void ping_ReturnsPongResponse() {
        ResponseEntity<String> response = clientController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }

    @Test
    void register_ReturnsBadRequestResponse_WhenRequestHasErrors() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("")
                .socialReason("")
                .email("invalid-email")
                .password("")
                .build();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.BAD_REQUEST.value());

        when(bindingResult.hasErrors()).thenReturn(true);
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.register(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void registerClient_ReturnsCreatedResponse_WhenIsSuccessfullyRegistered() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.CREATED.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.register(request)).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.register(request, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void register_ReturnsInternalServerErrorResponse_WhenExceptionIsThrown() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.register(request)).thenReturn(responseServiceDto);
        when(apiUtils.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Unexpected error")).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.register(request, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void validateUser_ReturnsOkResponse_WhenParametersAreValid() {
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        when(clientService.validateUser("1010258471", "1", "1010258471")).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.validateUser(
                "1010258471", "1", "1010258471");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void validateUser_ReturnsServiceResponse_WhenCalled() {
        // Arrange
        String numeroDocumentoCliente = "1010258471";
        String tipoDocumentoUsuario = "1";
        String numeroDocumentoUsuario = "1010478914";

        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        when(clientService.validateUser(numeroDocumentoCliente, tipoDocumentoUsuario, numeroDocumentoUsuario))
                .thenReturn(responseServiceDto);

        // Act
        ResponseEntity<ResponseServiceDto> response = clientController.validateUser(
                numeroDocumentoCliente, tipoDocumentoUsuario, numeroDocumentoUsuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
        verify(clientService).validateUser(numeroDocumentoCliente, tipoDocumentoUsuario, numeroDocumentoUsuario);
    }

    @Test
    void list_ReturnsOkResponse_WhenClientsExist() {
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        when(clientService.list()).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }

    @Test
    void documentList_ReturnsOkResponse_WhenDocumentTypesExist() {
        ResponseServiceDto responseServiceDto = new ResponseServiceDto();
        responseServiceDto.setStatusCode(HttpStatus.OK.value());

        when(clientService.documentTypeList()).thenReturn(responseServiceDto);

        ResponseEntity<ResponseServiceDto> response = clientController.documentList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseServiceDto, response.getBody());
    }
}