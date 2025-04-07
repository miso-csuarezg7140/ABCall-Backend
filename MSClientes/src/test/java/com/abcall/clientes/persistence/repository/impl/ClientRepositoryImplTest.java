package com.abcall.clientes.persistence.repository.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.persistence.entity.Client;
import com.abcall.clientes.persistence.mappers.IClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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
}