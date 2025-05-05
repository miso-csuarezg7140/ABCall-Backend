package com.abcall.clientes.security;

import com.abcall.clientes.domain.dto.ClientAuthenticationInfo;
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

    public ClientAuthenticationInfo getAuthenticationInfoFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return ClientAuthenticationInfo.builder()
                .subject(claims.getSubject())
                .clientId(Integer.parseInt(claims.get("clientId").toString()))
                .documentNumber(claims.get("documentNumber", Long.class))
                .socialReason(claims.get("socialReason").toString())
                .email(claims.get("email").toString())
                .roles((List<String>) claims.get("roles"))
                .build();

    }
}
