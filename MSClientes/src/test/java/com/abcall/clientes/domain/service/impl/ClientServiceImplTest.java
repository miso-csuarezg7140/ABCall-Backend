package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.DocumentTypeDto;
import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.persistence.repository.IClientRepository;
import com.abcall.clientes.persistence.repository.IDocumentTypeRepository;
import com.abcall.clientes.persistence.repository.IUserClientRepository;
import com.abcall.clientes.util.ApiUtils;
import com.abcall.clientes.util.enums.HttpResponseCodes;
import com.abcall.clientes.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private IUserClientRepository userClientRepository;

    @Mock
    private IDocumentTypeRepository documentTypeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void authenticate_ReturnsOkResponse_WhenCredentialsAreValid() {
        ClientDto clientDto = new ClientDto();
        clientDto.setPassword("encodedPassword");
        clientDto.setIdClient(1);
        clientDto.setDocumentNumber(123456L);
        clientDto.setSocialReason("Test Company");
        clientDto.setEmail("test@example.com");

        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(clientDto);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientService.authenticate("123456", "password");

        assertNotNull(response);
        verify(clientRepository).save(clientDto);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any());
    }

    @Test
    void authenticate_ReturnsUnauthorizedResponse_WhenPasswordIsInvalid() {
        ClientDto clientDto = new ClientDto();
        clientDto.setPassword("encodedPassword");

        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(clientDto);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientService.authenticate("123456", "wrongPassword");

        assertNotNull(response);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()),
                eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any());
    }

    @Test
    void authenticate_ReturnsBusinessMistakeResponse_WhenClientNotFound() {
        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(null);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientService.authenticate("123456", "password");

        assertNotNull(response);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()), any());
    }

    @Test
    void register_ReturnsCreatedResponse_WhenClientIsRegisteredSuccessfully() {
        ClientRegisterRequest request = new ClientRegisterRequest();
        request.setDocumentNumber("123456");
        request.setPassword("password");
        request.setSocialReason("Test Company");
        request.setEmail("test@example.com");

        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(clientRepository.save(any(ClientDto.class))).thenReturn(new ClientDto());
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientService.register(request);

        assertNotNull(response);
        verify(clientRepository).save(any(ClientDto.class));
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.CREATED.getCode()),
                eq(HttpResponseMessages.CREATED.getMessage()), any());
    }

    @Test
    void register_ReturnsBusinessMistakeResponse_WhenClientAlreadyExists() {
        ClientRegisterRequest request = new ClientRegisterRequest();
        request.setDocumentNumber("123456");

        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(new ClientDto());
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientService.register(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.BUSINESS_MISTAKE.getMessage()), any());
    }

    @Test
    void validateUser_ReturnsOkResponse_WhenUserClientAssociationExists() {
        // Arrange
        String documentClientStr = "123456";
        String documentTypeUserStr = "1";
        String documentUserStr = "123456789";

        ClientDto clientDto = new ClientDto();
        clientDto.setIdClient(1);

        UserClientDto userClientDto = new UserClientDto(1, 123456789L, 1);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());

        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(clientDto);
        when(userClientRepository.findById(any(UserClientDto.class))).thenReturn(userClientDto);
        when(apiUtils.buildResponse(
                eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()),
                any(UserClientDto.class)))
                .thenReturn(expectedResponse);

        // Act
        ResponseServiceDto actualResponse = clientService.validateUser(
                documentClientStr, documentTypeUserStr, documentUserStr);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(HttpResponseCodes.OK.getCode(), actualResponse.getStatusCode());

        // Verify
        verify(clientRepository).findByDocumentNumber(123456L);
        verify(userClientRepository).findById(argThat((UserClientDto dto) ->
                dto.getDocumentTypeUser() == 1 &&
                        dto.getDocumentUser() == 123456789L &&
                        dto.getIdClient() == 1
        ));
        verify(apiUtils).buildResponse(
                eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()),
                argThat((UserClientDto dto) ->
                        dto.getDocumentTypeUser() == 1 &&
                                dto.getDocumentUser() == 123456789L &&
                                dto.getIdClient() == 1
                )
        );
    }

    @Test
    void validateUser_ReturnsBusinessMistakeResponse_WhenUserClientAssociationNotFound() {
        // Arrange
        String documentClientStr = "123456";
        String documentTypeUserStr = "1";
        String documentUserStr = "123456789";

        ClientDto clientDto = new ClientDto();
        clientDto.setIdClient(1);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.BUSINESS_MISTAKE.getCode());

        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(clientDto);
        when(userClientRepository.findById(any(UserClientDto.class))).thenReturn(null);
        when(apiUtils.buildResponse(
                eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.BUSINESS_MISTAKE.getMessage()),
                any(HashMap.class)))
                .thenReturn(expectedResponse);

        // Act
        ResponseServiceDto response = clientService.validateUser(
                documentClientStr, documentTypeUserStr, documentUserStr);

        // Assert
        assertNotNull(response);
        assertEquals(HttpResponseCodes.BUSINESS_MISTAKE.getCode(), response.getStatusCode());

        // Verify
        verify(clientRepository).findByDocumentNumber(123456L);
        verify(userClientRepository).findById(argThat((UserClientDto dto) ->
                dto.getDocumentTypeUser() == 1 &&
                        dto.getDocumentUser() == 123456789L &&
                        dto.getIdClient() == 1
        ));
        verify(apiUtils).buildResponse(
                eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.BUSINESS_MISTAKE.getMessage()),
                any(HashMap.class)
        );
    }

    @Test
    void validateUser_ReturnsBusinessMistakeResponse_WhenClientNotFound() {
        when(clientRepository.findByDocumentNumber(123456L)).thenReturn(null);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientService.validateUser("123456", "1",
                "123456789");

        assertNotNull(response);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.BUSINESS_MISTAKE.getCode()),
                eq(HttpResponseMessages.BUSINESS_MISTAKE.getMessage()), any());
    }

    @Test
    void list_ReturnsOkResponse_WhenActiveClientsExist() {
        // Arrange
        List<ListClientResponse> mockClients = List.of(new ListClientResponse());
        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());

        when(clientRepository.findActiveClients()).thenReturn(mockClients);
        when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockClients))
                .thenReturn(expectedResponse);

        // Act
        ResponseServiceDto response = clientService.list();

        // Assert
        assertNotNull(response);
        assertEquals(HttpResponseCodes.OK.getCode(), response.getStatusCode());
        verify(clientRepository).findActiveClients();
        verify(apiUtils).buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockClients);
    }

    @Test
    void list_ReturnsNoContentResponse_WhenNoActiveClientsExist() {
        // Arrange
        when(clientRepository.findActiveClients()).thenReturn(List.of());
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.list();

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(
                eq(HttpResponseCodes.NO_CONTENT.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()),
                any(HashMap.class));
    }

    @Test
    void list_ReturnsErrorResponse_WhenExceptionOccurs() {
        // Arrange
        String errorMessage = "Database error";
        when(clientRepository.findActiveClients()).thenThrow(new RuntimeException(errorMessage));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.list();

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), errorMessage);
    }

    @Test
    void register_ReturnsErrorResponse_WhenExceptionOccurs() {
        // Arrange
        String errorMessage = "Database error";
        ClientRegisterRequest request = new ClientRegisterRequest();
        request.setDocumentNumber("123456");
        when(clientRepository.findByDocumentNumber(123456L)).thenThrow(new RuntimeException(errorMessage));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.register(request);

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), errorMessage);
    }

    @Test
    void authenticate_ReturnsErrorResponse_WhenExceptionOccurs() {
        // Arrange
        String errorMessage = "Database error";
        when(clientRepository.findByDocumentNumber(any())).thenThrow(new RuntimeException(errorMessage));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.authenticate("123456", "password");

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), errorMessage);
    }

    @Test
    void validateUser_ReturnsErrorResponse_WhenExceptionOccurs() {
        // Arrange
        String errorMessage = "Database error";
        when(clientRepository.findByDocumentNumber(any())).thenThrow(new RuntimeException(errorMessage));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.validateUser("123456", "1",
                "123456789");

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), errorMessage);
    }

    @Test
    void documentTypeList_ReturnsOkResponse_WhenDocumentTypesExist() {
        // Arrange
        List<DocumentTypeDto> mockDocumentTypes = List.of(new DocumentTypeDto());
        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());

        when(documentTypeRepository.getList()).thenReturn(mockDocumentTypes);
        when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockDocumentTypes))
                .thenReturn(expectedResponse);

        // Act
        ResponseServiceDto response = clientService.documentTypeList();

        // Assert
        assertNotNull(response);
        assertEquals(HttpResponseCodes.OK.getCode(), response.getStatusCode());
        verify(documentTypeRepository).getList();
        verify(apiUtils).buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), mockDocumentTypes);
    }

    @Test
    void documentTypeList_ReturnsNoContentResponse_WhenNoDocumentTypesExist() {
        // Arrange
        when(documentTypeRepository.getList()).thenReturn(List.of());
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.documentTypeList();

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(
                eq(HttpResponseCodes.NO_CONTENT.getCode()),
                eq(HttpResponseMessages.NO_CONTENT.getMessage()),
                any(HashMap.class));
    }

    @Test
    void documentTypeList_ReturnsErrorResponse_WhenExceptionOccurs() {
        // Arrange
        String errorMessage = "Database error";
        when(documentTypeRepository.getList()).thenThrow(new RuntimeException(errorMessage));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        // Act
        ResponseServiceDto response = clientService.documentTypeList();

        // Assert
        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), errorMessage);
    }
}