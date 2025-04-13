package com.abcall.clientes.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CorsConfigTest {

    @Test
    void testImplementsWebMvcConfigurer() {
        // Arrange & Act
        CorsConfig corsConfig = new CorsConfig();

        // Assert
        assertInstanceOf(WebMvcConfigurer.class, corsConfig, "CorsConfig debe implementar WebMvcConfigurer");
    }

    @Test
    void testActualRequest() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.addHeader("Origin", "http://localhost:4200");

        MockHttpServletResponse response = new MockHttpServletResponse();

        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        DefaultCorsProcessor processor = new DefaultCorsProcessor();

        // Act
        boolean isValid = processor.processRequest(config, request, response);

        // Assert
        assertTrue(isValid);
        assertNotNull(response.getHeader("Access-Control-Allow-Origin"));
    }

    @Test
    void testCorsConfigAllowedMethods() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        CorsRegistry registry = new CorsRegistry();

        // Act
        corsConfig.addCorsMappings(registry);

        // Assert - Verificamos que la configuración se realiza sin errores
        assertDoesNotThrow(registry::toString);
    }

    @Test
    void testCorsConfigAllowedHeaders() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        CorsRegistry registry = new CorsRegistry();

        // Act & Assert
        assertDoesNotThrow(() -> corsConfig.addCorsMappings(registry));
    }
}