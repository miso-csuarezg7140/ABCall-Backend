package com.abcall.clientes.security;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EncoderConfigTest {

    @Test
    void passwordEncoder_BeanIsCreatedSuccessfully() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EncoderConfig.class)) {
            PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
            assertNotNull(passwordEncoder);
            assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);
        }
    }

    @Test
    void securityContextHolderStrategy_BeanIsCreatedSuccessfully() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EncoderConfig.class)) {
            SecurityContextHolderStrategy strategy = context.getBean(SecurityContextHolderStrategy.class);
            assertNotNull(strategy);
            assertEquals(SecurityContextHolder.getContextHolderStrategy(), strategy);
        }
    }
}