package com.abcall.agentes.security;

import com.abcall.agentes.domain.dto.AgentAuthenticationInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    String jwtSecret;

    @Value("${jwt.access.expiration.ms}")
    private String jwtExpirationMs;

    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public AgentAuthenticationInfo getAuthenticationInfoFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return AgentAuthenticationInfo.builder()
                .subject(claims.getSubject())
                .documentType(Integer.parseInt(claims.get("documentType").toString()))
                .documentNumber(claims.get("documentNumber").toString())
                .names(claims.get("names").toString())
                .surnames(claims.get("surnames").toString())
                .roles((List<String>) claims.get("roles"))
                .build();

    }
}