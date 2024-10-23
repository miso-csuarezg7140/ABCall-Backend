package com.abcall.reporteria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OpenApiConfigTest {

    @Test
    void testOpenAPIBean() {
        // Arrange
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OpenApiConfig.class);

        // Act
        OpenAPI openAPI = context.getBean(OpenAPI.class);

        // Assert
        assertNotNull(openAPI);

        context.close();
    }

    @Test
    void testCustomOpenAPIConfiguration() {
        // Arrange
        OpenApiConfig config = new OpenApiConfig();

        // Act
        OpenAPI openAPI = config.customOpenAPI();

        // Assert
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());

        Info info = openAPI.getInfo();
        assertEquals("API MS Reportería", info.getTitle());
        assertEquals("1.0", info.getVersion());
        assertEquals("Servicio que gestiona la reportería de ABCall.", info.getDescription());
    }

    @Test
    void testInfoNotNull() {
        // Arrange
        OpenApiConfig config = new OpenApiConfig();

        // Act
        OpenAPI openAPI = config.customOpenAPI();

        // Assert
        assertNotNull(openAPI.getInfo());
    }

    @Test
    void testInfoFields() {
        // Arrange
        OpenApiConfig config = new OpenApiConfig();

        // Act
        OpenAPI openAPI = config.customOpenAPI();
        Info info = openAPI.getInfo();

        // Assert
        assertEquals("API MS Reportería", info.getTitle());
        assertEquals("1.0", info.getVersion());
        assertEquals("Servicio que gestiona la reportería de ABCall.", info.getDescription());
    }

    @Test
    void testNoAdditionalFields() {
        // Arrange
        OpenApiConfig config = new OpenApiConfig();

        // Act
        OpenAPI openAPI = config.customOpenAPI();

        // Assert
        assertNull(openAPI.getPaths());
        assertNull(openAPI.getComponents());
        assertNull(openAPI.getTags());
    }
}