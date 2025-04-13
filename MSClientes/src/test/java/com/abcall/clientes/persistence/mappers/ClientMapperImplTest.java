package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.persistence.entity.Client;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientMapperImplTest {

    private final IClientMapperImpl mapper = new IClientMapperImpl();

    @Test
    void toEntity_ReturnsNull_WhenClientDtoIsNull() {
        Client result = mapper.toEntity(null);
        assertNull(result);
    }

    @Test
    void toEntity_MapsFieldsCorrectly() {
        ClientDto clientDto = new ClientDto();
        clientDto.setIdClient(1);
        clientDto.setDocumentNumber(123456789L);
        clientDto.setPassword("password");
        clientDto.setSocialReason("ABC Corp");
        clientDto.setEmail("abc@corp.com");
        clientDto.setPlan("Premium");
        clientDto.setCreatedDate(LocalDateTime.now());
        clientDto.setUpdatedDate(LocalDateTime.now());
        clientDto.setLastLogin(LocalDateTime.now());
        clientDto.setStatus('A');

        Client result = mapper.toEntity(clientDto);

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
    void toEntityList_ReturnsNull_WhenClientDtoListIsNull() {
        List<Client> result = mapper.toEntityList(null);
        assertNull(result);
    }

    @Test
    void toEntityList_MapsListCorrectly() {
        List<ClientDto> clientDtoList = new ArrayList<>();
        ClientDto clientDto = new ClientDto();
        clientDto.setIdClient(1);
        clientDto.setDocumentNumber(123456789L);
        clientDto.setPassword("password");
        clientDto.setSocialReason("ABC Corp");
        clientDto.setEmail("abc@corp.com");
        clientDto.setPlan("Premium");
        clientDto.setCreatedDate(LocalDateTime.now());
        clientDto.setUpdatedDate(LocalDateTime.now());
        clientDto.setLastLogin(LocalDateTime.now());
        clientDto.setStatus('A');
        clientDtoList.add(clientDto);

        List<Client> result = mapper.toEntityList(clientDtoList);

        assertEquals(1, result.size());
        assertEquals(clientDto.getIdClient(), result.getFirst().getIdClient());
    }

    @Test
    void toDto_ReturnsNull_WhenClientIsNull() {
        ClientDto result = mapper.toDto(null);
        assertNull(result);
    }

    @Test
    void toDto_MapsFieldsCorrectly() {
        Client client = new Client();
        client.setIdClient(1);
        client.setDocumentNumber(123456789L);
        client.setPassword("password");
        client.setSocialReason("ABC Corp");
        client.setEmail("abc@corp.com");
        client.setPlan("Premium");
        client.setCreatedDate(LocalDateTime.now());
        client.setUpdatedDate(LocalDateTime.now());
        client.setLastLogin(LocalDateTime.now());
        client.setStatus("A");

        ClientDto result = mapper.toDto(client);

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
    void toDtoList_ReturnsNull_WhenClientListIsNull() {
        List<ClientDto> result = mapper.toDtoList(null);
        assertNull(result);
    }

    @Test
    void toDtoList_MapsListCorrectly() {
        List<Client> clientList = new ArrayList<>();
        Client client = new Client();
        client.setIdClient(1);
        client.setDocumentNumber(123456789L);
        client.setPassword("password");
        client.setSocialReason("ABC Corp");
        client.setEmail("abc@corp.com");
        client.setPlan("Premium");
        client.setCreatedDate(LocalDateTime.now());
        client.setUpdatedDate(LocalDateTime.now());
        client.setLastLogin(LocalDateTime.now());
        client.setStatus("A");
        clientList.add(client);

        List<ClientDto> result = mapper.toDtoList(clientList);

        assertEquals(1, result.size());
        assertEquals(client.getIdClient(), result.getFirst().getIdClient());
    }
}
