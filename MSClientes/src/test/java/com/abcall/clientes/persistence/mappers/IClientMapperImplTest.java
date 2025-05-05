package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.persistence.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class IClientMapperImplTest {

    private IClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = new IClientMapperImpl();
    }

    @Test
    void toEntity_ReturnsClient_WhenClientDtoIsValid() {
        ClientDto clientDto = ClientDto.builder()
                .idClient(1)
                .documentNumber(123456L)
                .password("password123")
                .socialReason("Test Company")
                .email("test@example.com")
                .plan("Basic")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .status('A')
                .build();

        Client result = clientMapper.toEntity(clientDto);

        assertEquals(clientDto.getIdClient(), result.getIdClient());
        assertEquals(clientDto.getDocumentNumber(), result.getDocumentNumber());
        assertEquals(clientDto.getPassword(), result.getPassword());
        assertEquals(clientDto.getSocialReason(), result.getSocialReason());
        assertEquals(clientDto.getEmail(), result.getEmail());
        assertEquals(clientDto.getPlan(), result.getPlan());
        assertEquals(clientDto.getCreatedDate(), result.getCreatedDate());
        assertEquals(clientDto.getUpdatedDate(), result.getUpdatedDate());
        assertEquals(clientDto.getLastLogin(), result.getLastLogin());
        assertEquals(clientDto.getStatus().toString(), result.getStatus());
    }

    @Test
    void toDto_ReturnsClientDto_WhenClientIsValid() {
        Client client = new Client();
        client.setIdClient(1);
        client.setDocumentNumber(123456L);
        client.setPassword("password123");
        client.setSocialReason("Test Company");
        client.setEmail("test@example.com");
        client.setPlan("Basic");
        client.setCreatedDate(LocalDateTime.now());
        client.setUpdatedDate(LocalDateTime.now());
        client.setLastLogin(LocalDateTime.now());
        client.setStatus("A");

        ClientDto result = clientMapper.toDto(client);

        assertEquals(client.getIdClient(), result.getIdClient());
        assertEquals(client.getDocumentNumber(), result.getDocumentNumber());
        assertEquals(client.getPassword(), result.getPassword());
        assertEquals(client.getSocialReason(), result.getSocialReason());
        assertEquals(client.getEmail(), result.getEmail());
        assertEquals(client.getPlan(), result.getPlan());
        assertEquals(client.getCreatedDate(), result.getCreatedDate());
        assertEquals(client.getUpdatedDate(), result.getUpdatedDate());
        assertEquals(client.getLastLogin(), result.getLastLogin());
        assertEquals(client.getStatus().charAt(0), result.getStatus());
    }

    @Test
    void toListDtoList_ReturnsListOfListClientResponse_WhenClientListIsValid() {
        Client client = new Client();
        client.setDocumentNumber(123456L);
        client.setSocialReason("Test Company");
        List<Client> clientList = List.of(client);

        List<ListClientResponse> result = clientMapper.toListDtoList(clientList);

        assertEquals(1, result.size());
        assertEquals(client.getDocumentNumber(), result.getFirst().getDocumentNumber());
        assertEquals(client.getSocialReason(), result.getFirst().getSocialReason());
    }

    @Test
    void toListDtoList_ReturnsEmptyList_WhenClientListIsEmpty() {
        List<Client> clientList = new ArrayList<>();

        List<ListClientResponse> result = clientMapper.toListDtoList(clientList);

        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_ReturnsNull_WhenClientDtoIsNull() {
        assertNull(clientMapper.toEntity(null));
    }

    @Test
    void toDto_ReturnsNull_WhenClientIsNull() {
        assertNull(clientMapper.toDto(null));
    }

    @Test
    void toListDtoList_ReturnsNull_WhenClientListIsNull() {
        assertNull(clientMapper.toListDtoList(null));
    }
}