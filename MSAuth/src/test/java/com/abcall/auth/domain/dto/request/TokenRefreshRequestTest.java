package com.abcall.auth.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TokenRefreshRequestTest {

    @Test
    void tokenRefreshRequestShouldStoreTokenCorrectly() {
        TokenRefreshRequest request = new TokenRefreshRequest("sampleRefreshToken");

        assertEquals("sampleRefreshToken", request.getToken());
    }

    @Test
    void tokenRefreshRequestShouldHandleNullToken() {
        TokenRefreshRequest request = new TokenRefreshRequest(null);

        assertNull(request.getToken());
    }

    @Test
    void tokenRefreshRequestShouldUpdateTokenCorrectly() {
        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setToken("updatedRefreshToken");

        assertEquals("updatedRefreshToken", request.getToken());
    }

    @Test
    void tokenRefreshRequestEqualsShouldWorkCorrectly() {
        TokenRefreshRequest request1 = new TokenRefreshRequest("sampleRefreshToken");
        TokenRefreshRequest request2 = new TokenRefreshRequest("sampleRefreshToken");
        TokenRefreshRequest request3 = new TokenRefreshRequest("differentToken");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}