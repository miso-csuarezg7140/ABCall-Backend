package com.abcall.incidentes.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class UserClientDtoResponseTest {

    @Test
    void creationSucceedsWithValidValues() {
        UserClientDtoResponse response = new UserClientDtoResponse("DNI", 12345678L, 1);

        assertEquals("DNI", response.getDocumentTypeUser());
        assertEquals(12345678L, response.getDocumentNumberUser());
        assertEquals(1, response.getIdClient());
    }

    @Test
    void creationSucceedsWithNoArgsConstructor() {
        UserClientDtoResponse response = new UserClientDtoResponse();

        response.setDocumentTypeUser("PASSPORT");
        response.setDocumentNumberUser(98765432L);
        response.setIdClient(2);

        assertEquals("PASSPORT", response.getDocumentTypeUser());
        assertEquals(98765432L, response.getDocumentNumberUser());
        assertEquals(2, response.getIdClient());
    }

    @Test
    void creationFailsWhenDocumentTypeUserIsNull() {
        UserClientDtoResponse response = new UserClientDtoResponse(null, 12345678L, 1);

        assertNull(response.getDocumentTypeUser());
        assertEquals(12345678L, response.getDocumentNumberUser());
        assertEquals(1, response.getIdClient());
    }

    @Test
    void creationFailsWhenDocumentNumberUserIsNull() {
        UserClientDtoResponse response = new UserClientDtoResponse("DNI", null, 1);

        assertEquals("DNI", response.getDocumentTypeUser());
        assertNull(response.getDocumentNumberUser());
        assertEquals(1, response.getIdClient());
    }

    @Test
    void creationFailsWhenIdClientIsNull() {
        UserClientDtoResponse response = new UserClientDtoResponse("DNI", 12345678L, null);

        assertEquals("DNI", response.getDocumentTypeUser());
        assertEquals(12345678L, response.getDocumentNumberUser());
        assertNull(response.getIdClient());
    }
}