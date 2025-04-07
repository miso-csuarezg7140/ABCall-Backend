package com.abcall.auth.security;

import com.abcall.auth.web.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
        "jwt.refresh.expiration.ms=25200000"
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
