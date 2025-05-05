package com.abcall.clientes.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserClientDtoTest {

    @Test
    void constructor_CreatesObjectWithCorrectValues() {
        // Arrange
        Integer documentTypeUser = 1;
        Long documentUser = 123456789L;
        Integer idClient = 100;

        // Act
        UserClientDto dto = new UserClientDto(documentTypeUser, documentUser, idClient);

        // Assert
        assertEquals(documentTypeUser, dto.getDocumentTypeUser());
        assertEquals(documentUser, dto.getDocumentUser());
        assertEquals(idClient, dto.getIdClient());
    }

    @Test
    void constructor_WorksWithNullValues() {
        // Act
        UserClientDto dto = new UserClientDto(null, null, null);

        // Assert
        assertNull(dto.getDocumentTypeUser());
        assertNull(dto.getDocumentUser());
        assertNull(dto.getIdClient());
    }

    @Test
    void setters_UpdateValuesCorrectly() {
        // Arrange
        UserClientDto dto = new UserClientDto(1, 123456789L, 100);
        Integer newDocumentTypeUser = 2;
        Long newDocumentUser = 987654321L;
        Integer newIdClient = 200;

        // Act
        dto.setDocumentTypeUser(newDocumentTypeUser);
        dto.setDocumentUser(newDocumentUser);
        dto.setIdClient(newIdClient);

        // Assert
        assertEquals(newDocumentTypeUser, dto.getDocumentTypeUser());
        assertEquals(newDocumentUser, dto.getDocumentUser());
        assertEquals(newIdClient, dto.getIdClient());
    }

    @Test
    void setters_WorkWithNullValues() {
        // Arrange
        UserClientDto dto = new UserClientDto(1, 123456789L, 100);

        // Act
        dto.setDocumentTypeUser(null);
        dto.setDocumentUser(null);
        dto.setIdClient(null);

        // Assert
        assertNull(dto.getDocumentTypeUser());
        assertNull(dto.getDocumentUser());
        assertNull(dto.getIdClient());
    }

    @Test
    void getters_ReturnCorrectValues() {
        // Arrange
        Integer documentTypeUser = 3;
        Long documentUser = 555555555L;
        Integer idClient = 300;
        UserClientDto dto = new UserClientDto(documentTypeUser, documentUser, idClient);

        // Act & Assert
        assertEquals(documentTypeUser, dto.getDocumentTypeUser());
        assertEquals(documentUser, dto.getDocumentUser());
        assertEquals(idClient, dto.getIdClient());
    }

    @Test
    void objectEquality_DifferentInstancesWithSameValues() {
        // Arrange
        UserClientDto dto1 = new UserClientDto(1, 123456789L, 100);
        UserClientDto dto2 = new UserClientDto(1, 123456789L, 100);

        // Assert
        // Note: Since equals() is not overridden, different instances are not equal
        assertNotEquals(dto1, dto2);
        assertNotSame(dto1, dto2);
    }

    @Test
    void objectEquality_SameInstance() {
        // Arrange
        UserClientDto dto = new UserClientDto(1, 123456789L, 100);

        // Assert
        assertEquals(dto, dto);
        assertSame(dto, dto);
    }

    @Test
    void toString_DefaultImplementation() {
        // Arrange
        UserClientDto dto = new UserClientDto(1, 123456789L, 100);

        // Act
        String result = dto.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains(dto.getClass().getName()));
    }

    @Test
    void hashCode_ConsistentForSameObject() {
        // Arrange
        UserClientDto dto = new UserClientDto(1, 123456789L, 100);

        // Act
        int hashCode1 = dto.hashCode();
        int hashCode2 = dto.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_DifferentForDifferentObjects() {
        // Arrange
        UserClientDto dto1 = new UserClientDto(1, 123456789L, 100);
        UserClientDto dto2 = new UserClientDto(2, 987654321L, 200);

        // Act
        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        // Assert
        // Note: Different objects might have the same hashCode (collision),
        // but it's very unlikely with different values
        // This test might occasionally fail due to hash collisions
        assertNotEquals(hashCode1, hashCode2);
    }
}