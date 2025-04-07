package com.abcall.auth.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AgentAuthRequestTest {

    @Test
    void agentAuthRequestShouldStoreUsernameAndPassword() {
        AgentAuthRequest request = new AgentAuthRequest("user", "pass");
        assertEquals("user", request.getUsername());
        assertEquals("pass", request.getPassword());
    }

    @Test
    void agentAuthRequestShouldHandleEmptyUsernameAndPassword() {
        AgentAuthRequest request = new AgentAuthRequest("", "");
        assertEquals("", request.getUsername());
        assertEquals("", request.getPassword());
    }

    @Test
    void agentAuthRequestShouldHandleNullUsernameAndPassword() {
        AgentAuthRequest request = new AgentAuthRequest(null, null);
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }
}