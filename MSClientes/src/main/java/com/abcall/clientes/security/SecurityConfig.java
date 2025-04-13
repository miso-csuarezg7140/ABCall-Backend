package com.abcall.clientes.security;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        /*httpSecurity
                .csrf(csrf -> csrf.ignoringRequestMatchers("/ping", "/authenticate",
                        "/swagger-ui.html/**", "/v3/api-docs/**", "/register"))
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((
                                request, response, ex) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized\"}");
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ping", "/authenticate", "/swagger-ui.html/**",
                                "/v3/api-docs/**", "/register").permitAll()
                        .anyRequest().authenticated()
                );*/
        httpSecurity
                .csrf(csrf -> csrf.ignoringRequestMatchers("/ping", "/authenticate",
                        "/swagger-ui.html/**", "/v3/api-docs/**", "/register"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/ping", "/authenticate", "/swagger-ui.html/**",
                                "/v3/api-docs/**", "/register").permitAll().anyRequest().authenticated()
                );

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
