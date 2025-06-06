package com.abcall.clientes.persistence.entity;

import com.abcall.clientes.persistence.entity.compositekey.UserClientPK;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserClientTest {

    @Test
    void userClient_HasCorrectEmbeddedId() {
        UserClientPK pk = new UserClientPK();
        pk.setDocumentTypeUser(1);
        pk.setDocumentUser(123456L);
        pk.setIdClient(789012);

        UserClient userClient = new UserClient();
        userClient.setUserClientPK(pk);

        assertNotNull(userClient.getUserClientPK());
        assertEquals(1, userClient.getUserClientPK().getDocumentTypeUser());
        assertEquals(123456L, userClient.getUserClientPK().getDocumentUser());
        assertEquals(789012, userClient.getUserClientPK().getIdClient());
    }

    @Test
    void userClient_AllowsNullEmbeddedId() {
        UserClient userClient = new UserClient();
        userClient.setUserClientPK(null);

        assertNull(userClient.getUserClientPK());
    }
}