package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.persistence.entity.UserClient;
import com.abcall.clientes.persistence.entity.compositekey.UserClientPK;
import com.abcall.clientes.persistence.mappers.IUserClientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserClientRepositoryImplTest {

    @Mock
    private IUserClientRepositoryJpa userClientRepositoryJpa;

    @Mock
    private IUserClientMapper userClientMapper;

    @InjectMocks
    private UserClientRepositoryImpl userClientRepository;

    @Test
    void findById_ReturnsUserClientDto_WhenEntityExists() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(1, 123456789L, 100);
        UserClientPK userClientPK = new UserClientPK(1, 123456789L, 100);
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        UserClient foundUserClient = new UserClient();
        foundUserClient.setUserClientPK(userClientPK);

        UserClientDto expectedDto = new UserClientDto(1, 123456789L, 100);

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        when(userClientRepositoryJpa.findById(userClientPK)).thenReturn(Optional.of(foundUserClient));
        when(userClientMapper.toDto(foundUserClient)).thenReturn(expectedDto);

        // Act
        UserClientDto result = userClientRepository.findById(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getDocumentTypeUser(), result.getDocumentTypeUser());
        assertEquals(expectedDto.getDocumentUser(), result.getDocumentUser());
        assertEquals(expectedDto.getIdClient(), result.getIdClient());

        verify(userClientMapper).toEntity(inputDto);
        verify(userClientRepositoryJpa).findById(userClientPK);
        verify(userClientMapper).toDto(foundUserClient);
    }

    @Test
    void findById_ReturnsNull_WhenEntityDoesNotExist() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(1, 123456789L, 100);
        UserClientPK userClientPK = new UserClientPK(1, 123456789L, 100);
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        when(userClientRepositoryJpa.findById(userClientPK)).thenReturn(Optional.empty());
        when(userClientMapper.toDto(null)).thenReturn(null);

        // Act
        UserClientDto result = userClientRepository.findById(inputDto);

        // Assert
        assertNull(result);

        verify(userClientMapper).toEntity(inputDto);
        verify(userClientRepositoryJpa).findById(userClientPK);
        verify(userClientMapper).toDto(null);
    }

    @Test
    void findById_HandlesNullInput_WhenInputDtoIsNull() {
        // Arrange
        when(userClientMapper.toEntity(null)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> userClientRepository.findById(null));

        verify(userClientMapper).toEntity(null);
        verifyNoInteractions(userClientRepositoryJpa);
    }

    @Test
    void findById_HandlesNullPK_WhenEntityHasNullPK() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(1, 123456789L, 100);
        UserClient userClient = new UserClient();
        // userClientPK is null

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        // If we pass null to findById, it might throw an exception or return empty Optional
        when(userClientRepositoryJpa.findById(null)).thenReturn(Optional.empty());
        when(userClientMapper.toDto(null)).thenReturn(null);

        // Act
        UserClientDto result = userClientRepository.findById(inputDto);

        // Assert
        assertNull(result);

        verify(userClientMapper).toEntity(inputDto);
        verify(userClientRepositoryJpa).findById(null);
        verify(userClientMapper).toDto(null);
    }

    @Test
    void findById_ReturnsNull_WhenMapperReturnsDtoAsNullForExistingEntity() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(1, 123456789L, 100);
        UserClientPK userClientPK = new UserClientPK(1, 123456789L, 100);
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        UserClient foundUserClient = new UserClient();
        foundUserClient.setUserClientPK(userClientPK);

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        when(userClientRepositoryJpa.findById(userClientPK)).thenReturn(Optional.of(foundUserClient));
        when(userClientMapper.toDto(foundUserClient)).thenReturn(null); // Mapper returns null for existing entity

        // Act
        UserClientDto result = userClientRepository.findById(inputDto);

        // Assert
        assertNull(result);

        verify(userClientMapper).toEntity(inputDto);
        verify(userClientRepositoryJpa).findById(userClientPK);
        verify(userClientMapper).toDto(foundUserClient);
    }

    @Test
    void findById_VerifiesCorrectMethodCalls() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(2, 987654321L, 200);
        UserClientPK userClientPK = new UserClientPK(2, 987654321L, 200);
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        UserClient foundUserClient = new UserClient();
        foundUserClient.setUserClientPK(userClientPK);

        UserClientDto expectedDto = new UserClientDto(2, 987654321L, 200);

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        when(userClientRepositoryJpa.findById(userClientPK)).thenReturn(Optional.of(foundUserClient));
        when(userClientMapper.toDto(foundUserClient)).thenReturn(expectedDto);

        // Act
        userClientRepository.findById(inputDto);

        // Assert
        verify(userClientMapper, times(1)).toEntity(inputDto);
        verify(userClientRepositoryJpa, times(1)).findById(userClientPK);
        verify(userClientMapper, times(1)).toDto(foundUserClient);

        // Verify no more interactions
        verifyNoMoreInteractions(userClientMapper);
        verifyNoMoreInteractions(userClientRepositoryJpa);
    }

    @Test
    void findById_WorksWithDifferentDataTypes() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(3, 999999999L, 999);
        UserClientPK userClientPK = new UserClientPK(3, 999999999L, 999);
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        UserClient foundUserClient = new UserClient();
        foundUserClient.setUserClientPK(userClientPK);

        UserClientDto expectedDto = new UserClientDto(3, 999999999L, 999);

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        when(userClientRepositoryJpa.findById(userClientPK)).thenReturn(Optional.of(foundUserClient));
        when(userClientMapper.toDto(foundUserClient)).thenReturn(expectedDto);

        // Act
        UserClientDto result = userClientRepository.findById(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getDocumentTypeUser());
        assertEquals(999999999L, result.getDocumentUser());
        assertEquals(999, result.getIdClient());
    }

    // This test case is now replaced by findById_ReturnsNull_WhenMapperReturnsDtoAsNullForExistingEntity

    @Test
    void findById_HandlesExceptionFromJpaRepository() {
        // Arrange
        UserClientDto inputDto = new UserClientDto(1, 123456789L, 100);
        UserClientPK userClientPK = new UserClientPK(1, 123456789L, 100);
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(userClientPK);

        when(userClientMapper.toEntity(inputDto)).thenReturn(userClient);
        when(userClientRepositoryJpa.findById(userClientPK)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userClientRepository.findById(inputDto));

        assertEquals("Database error", exception.getMessage());

        verify(userClientMapper).toEntity(inputDto);
        verify(userClientRepositoryJpa).findById(userClientPK);
        verify(userClientMapper, never()).toDto(any());
    }
}