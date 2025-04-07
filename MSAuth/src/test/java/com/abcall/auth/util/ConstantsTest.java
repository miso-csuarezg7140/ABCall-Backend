package com.abcall.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConstantsTest {

    @Test
    void constantsClassShouldThrowExceptionWhenInstantiated() {
        assertThrows(IllegalStateException.class, Constants::new);
    }
}