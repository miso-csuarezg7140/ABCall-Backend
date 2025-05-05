package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.persistence.entity.UserClient;
import com.abcall.clientes.persistence.entity.compositekey.UserClientPK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class IUserClientMapperImplTest {

    private IUserClientMapper userClientMapper;

    @BeforeEach
    void setUp() {
        userClientMapper = new IUserClientMapperImpl();
    }

    @Test
    void toEntity_ReturnsUserClient_WhenUserClientDtoIsValid() {
        UserClientDto userClientDto = new UserClientDto(1, 123456789L, 100);

        UserClient result = userClientMapper.toEntity(userClientDto);

        assertNotNull(result);
        assertNotNull(result.getUserClientPK());
        assertEquals(userClientDto.getDocumentTypeUser(), result.getUserClientPK().getDocumentTypeUser());
        assertEquals(userClientDto.getDocumentUser(), result.getUserClientPK().getDocumentUser());
        assertEquals(userClientDto.getIdClient(), result.getUserClientPK().getIdClient());
    }

    @Test
    void toEntity_ReturnsNull_WhenUserClientDtoIsNull() {
        UserClient result = userClientMapper.toEntity(null);

        assertNull(result);
    }

    @Test
    void toDto_ReturnsUserClientDto_WhenUserClientIsValid() {
        UserClientPK userClientPK = new UserClientPK();
        userClientPK.setDocumentTypeUser(1);
        userClientPK.setDocumentUser(123456789L);
        userClientPK.setIdClient(100);

        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        UserClientDto result = userClientMapper.toDto(userClient);

        assertNotNull(result);
        assertEquals(userClientPK.getDocumentTypeUser(), result.getDocumentTypeUser());
        assertEquals(userClientPK.getDocumentUser(), result.getDocumentUser());
        assertEquals(userClientPK.getIdClient(), result.getIdClient());
    }

    @Test
    void toDto_ReturnsNull_WhenUserClientIsNull() {
        UserClientDto result = userClientMapper.toDto(null);

        assertNull(result);
    }

    @Test
    void toDto_ReturnsNullFields_WhenUserClientPKIsNull() {
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(null);

        UserClientDto result = userClientMapper.toDto(userClient);

        assertNotNull(result);
        assertNull(result.getDocumentTypeUser());
        assertNull(result.getDocumentUser());
        assertNull(result.getIdClient());
    }
}