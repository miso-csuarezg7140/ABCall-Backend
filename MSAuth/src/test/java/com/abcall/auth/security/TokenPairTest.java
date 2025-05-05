package com.abcall.auth.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TokenPairTest {

    @Test
    void tokenPairShouldStoreAccessTokenAndRefreshTokenCorrectly() {
        TokenPair tokenPair = new TokenPair("accessToken123", "refreshToken456");

        assertEquals("accessToken123", tokenPair.getAccessToken());
        assertEquals("refreshToken456", tokenPair.getRefreshToken());
    }

    @Test
    void tokenPairShouldHandleNullValues() {
        TokenPair tokenPair = new TokenPair(null, null);

        assertNull(tokenPair.getAccessToken());
        assertNull(tokenPair.getRefreshToken());
    }

    @Test
    void tokenPairShouldUpdateAccessTokenAndRefreshTokenCorrectly() {
        TokenPair tokenPair = new TokenPair("initialAccessToken", "initialRefreshToken");

        tokenPair.setAccessToken("updatedAccessToken");
        tokenPair.setRefreshToken("updatedRefreshToken");

        assertEquals("updatedAccessToken", tokenPair.getAccessToken());
        assertEquals("updatedRefreshToken", tokenPair.getRefreshToken());
    }

    @Test
    void tokenPairEqualsShouldWorkCorrectly() {
        TokenPair tokenPair1 = new TokenPair("accessToken123", "refreshToken456");
        TokenPair tokenPair2 = new TokenPair("accessToken123", "refreshToken456");
        TokenPair tokenPair3 = new TokenPair("differentAccessToken", "refreshToken456");

        assertEquals(tokenPair1, tokenPair2);
        assertNotEquals(tokenPair1, tokenPair3);
        assertEquals(tokenPair1.hashCode(), tokenPair2.hashCode());
    }
}