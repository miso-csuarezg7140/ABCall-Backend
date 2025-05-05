package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.persistence.entity.Client;
import com.abcall.clientes.persistence.mappers.IClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientRepositoryImplTest {

    @Mock
    private IClientRepositoryJpa clienteRepositoryJpa;

    @Mock
    private IClientMapper clientMapper;

    @InjectMocks
    private ClientRepositoryImpl clientRepositoryImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByDocumentNumber_ReturnsClientDto_WhenClientExists() {
        Long documentNumber = 123L;
        Client client = new Client();
        ClientDto clientDto = new ClientDto();

        when(clienteRepositoryJpa.findByDocumentNumber(documentNumber)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientDto);

        ClientDto result = clientRepositoryImpl.findByDocumentNumber(documentNumber);

        assertNotNull(result);
        assertEquals(clientDto, result);
    }

    @Test
    void findByDocumentNumber_ReturnsNull_WhenClientDoesNotExist() {
        Long documentNumber = 123L;

        when(clienteRepositoryJpa.findByDocumentNumber(documentNumber)).thenReturn(Optional.empty());

        ClientDto result = clientRepositoryImpl.findByDocumentNumber(documentNumber);

        assertNull(result);
    }

    @Test
    void save_SavesClient_WhenClientDtoIsProvided() {
        ClientDto clientDto = new ClientDto();
        Client client = new Client();

        when(clientMapper.toEntity(clientDto)).thenReturn(client);

        clientRepositoryImpl.save(clientDto);

        verify(clienteRepositoryJpa, times(1)).save(client);
    }

    @Test
    void findActiveClients_ReturnsListOfClients_WhenActiveClientsExist() {
        List<Client> activeClients = List.of(new Client(), new Client());
        List<ListClientResponse> activeClientDtos = List.of(new ListClientResponse(), new ListClientResponse());

        when(clienteRepositoryJpa.findActiveClients()).thenReturn(Optional.of(activeClients));
        when(clientMapper.toListDtoList(activeClients)).thenReturn(activeClientDtos);

        List<ListClientResponse> result = clientRepositoryImpl.findActiveClients();

        assertNotNull(result);
        assertEquals(activeClientDtos, result);
    }

    @Test
    void findActiveClients_ReturnsEmptyList_WhenNoActiveClientsExist() {
        when(clienteRepositoryJpa.findActiveClients()).thenReturn(Optional.empty());
        when(clientMapper.toListDtoList(null)).thenReturn(List.of());

        List<ListClientResponse> result = clientRepositoryImpl.findActiveClients();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}