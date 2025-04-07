package com.abcall.auth.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClientAuthRequestTest {

    @Test
    void clientAuthRequestShouldStoreUsernameAndPassword() {
        ClientAuthRequest request = new ClientAuthRequest("user", "pass");
        assertEquals("user", request.getUsername());
        assertEquals("pass", request.getPassword());
    }

    @Test
    void clientAuthRequestShouldHandleEmptyUsernameAndPassword() {
        ClientAuthRequest request = new ClientAuthRequest("", "");
        assertEquals("", request.getUsername());
        assertEquals("", request.getPassword());
    }

    @Test
    void clientAuthRequestShouldHandleNullUsernameAndPassword() {
        ClientAuthRequest request = new ClientAuthRequest(null, null);
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }
}