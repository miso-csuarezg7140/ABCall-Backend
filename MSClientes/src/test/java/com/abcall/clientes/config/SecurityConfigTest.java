package com.abcall.clientes.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.cloud.openfeign.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "agent.service.url=http://localhost:8080/",
        "client.service.url=http://localhost:8081/",
        "jwt.secret=test-secret-key-for-jwt-token-generation-used-in-testing-only",
        "jwt.access.expiration.ms=3600000",
        "jwt.refresh.expiration.ms=25200000",
        "server.port=8081",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.datasource.url=jdbc:postgresql://104.198.167.70:5432/clientes",
        "spring.datasource.username=postgres",
        "spring.datasource.password=dbadmin",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
        "spring.jpa.properties.hibernate.generate_statistics=false",
        "spring.jpa.properties.hibernate.jdbc.batch_size=30",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.hikari.maximum-pool-size=5",
        "spring.datasource.hikari.minimum-idle=2",
        "spring.jpa.open-in-view=false",
        "server.tomcat.threads.max=20",
        "server.tomcat.threads.min-spare=5"
})
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(
                SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    void passwordEncoderShouldEncodePassword() {
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void pingEndpointShouldBeAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());
    }

    @Test
    void otherEndpointsShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/secure-endpoint"))
                .andExpect(status().isForbidden());
    }

    @Test
    void csrfShouldBeDisabledForPingEndpoint() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());
    }

    @Test
    void csrfShouldBeEnabledForOtherEndpoints() throws Exception {
        mockMvc.perform(get("/secure-endpoint")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
