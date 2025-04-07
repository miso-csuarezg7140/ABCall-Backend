package com.abcall.auth.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoginRequestTest {

    @Test
    void loginRequestShouldStoreUsernamePasswordAndUserType() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");
        request.setUserType("admin");
        assertEquals("user", request.getUsername());
        assertEquals("pass", request.getPassword());
        assertEquals("admin", request.getUserType());
    }

    @Test
    void loginRequestShouldHandleEmptyUsernamePasswordAndUserType() {
        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword("");
        request.setUserType("");
        assertEquals("", request.getUsername());
        assertEquals("", request.getPassword());
        assertEquals("", request.getUserType());
    }

    @Test
    void loginRequestShouldHandleNullUsernamePasswordAndUserType() {
        LoginRequest request = new LoginRequest();
        request.setUsername(null);
        request.setPassword(null);
        request.setUserType(null);
        assertNull(request.getUsername());
        assertNull(request.getPassword());
        assertNull(request.getUserType());
    }
}