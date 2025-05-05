package com.abcall.auth.security;

import com.abcall.auth.domain.dto.response.AgentAuthResponse;
import com.abcall.auth.domain.dto.response.ClientAuthResponse;
import com.abcall.auth.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {

    final SecretKey key;
    private final int jwtExpirationMs;
    private final int refreshTokenExpirationMs;

    // Añadir expiración para refresh token
    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret,
                            @Value("${jwt.access.expiration.ms}") int jwtExpirationMs,
                            @Value("${jwt.refresh.expiration.ms}") int refreshTokenExpirationMs) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs = jwtExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    /**
     * Genera tokens (access y refresh) para un agente
     */
    public TokenPair generateAgentTokens(AgentAuthResponse agentAuthResponse) {
        // Generar access token
        String accessToken = generateAgentAccessToken(agentAuthResponse);

        // Generar refresh token
        String refreshToken = generateAgentRefreshToken(agentAuthResponse);

        return new TokenPair(accessToken, refreshToken);
    }

    /**
     * Genera tokens (access y refresh) para un cliente
     */
    public TokenPair generateClientTokens(ClientAuthResponse clientAuthResponse) {
        // Generar access token
        String accessToken = generateClientAccessToken(clientAuthResponse);

        // Generar refresh token
        String refreshToken = generateClientRefreshToken(clientAuthResponse);

        return new TokenPair(accessToken, refreshToken);
    }

    /**
     * Genera el access token para un agente
     */
    public String generateAgentAccessToken(AgentAuthResponse agentAuthResponse) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USER_TYPE, Constants.AGENT);
        claims.put(Constants.TOKEN_TYPE, "access");
        claims.put(Constants.DOCUMENT_TYPE, agentAuthResponse.getDocumentType());
        claims.put(Constants.DOCUMENT_NUMBER, agentAuthResponse.getDocumentNumber());
        claims.put("names", agentAuthResponse.getNames());
        claims.put("surnames", agentAuthResponse.getSurnames());
        claims.put(Constants.ROLES, agentAuthResponse.getRoles());

        String subject = "agent:" + agentAuthResponse.getDocumentType() + "-"
                + agentAuthResponse.getDocumentNumber();

        return generateToken(claims, subject, jwtExpirationMs);
    }

    /**
     * Genera el refresh token para un agente
     */
    public String generateAgentRefreshToken(AgentAuthResponse agentAuthResponse) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USER_TYPE, Constants.AGENT);
        claims.put(Constants.TOKEN_TYPE, Constants.REFRESH);
        claims.put(Constants.DOCUMENT_TYPE, agentAuthResponse.getDocumentType());
        claims.put(Constants.DOCUMENT_NUMBER, agentAuthResponse.getDocumentNumber());

        String subject = "agent:" + agentAuthResponse.getDocumentType() + "-"
                + agentAuthResponse.getDocumentNumber();

        return generateToken(claims, subject, refreshTokenExpirationMs);
    }

    /**
     * Genera el access token para un cliente
     */
    public String generateClientAccessToken(ClientAuthResponse clientAuthResponse) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USER_TYPE, Constants.CLIENT);
        claims.put(Constants.TOKEN_TYPE, "access");
        claims.put(Constants.CLIENT_ID, clientAuthResponse.getClientId());
        claims.put(Constants.DOCUMENT_NUMBER, clientAuthResponse.getDocumentNumber());
        claims.put("socialReason", clientAuthResponse.getSocialReason());
        claims.put("email", clientAuthResponse.getEmail());
        claims.put(Constants.ROLES, clientAuthResponse.getRoles());

        String subject = "client:" + clientAuthResponse.getClientId();

        return generateToken(claims, subject, jwtExpirationMs);
    }

    /**
     * Genera el refresh token para un cliente
     */
    public String generateClientRefreshToken(ClientAuthResponse clientAuthResponse) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USER_TYPE, Constants.CLIENT);
        claims.put(Constants.TOKEN_TYPE, Constants.REFRESH);
        claims.put(Constants.CLIENT_ID, clientAuthResponse.getClientId());
        claims.put(Constants.DOCUMENT_NUMBER, clientAuthResponse.getDocumentNumber());

        String subject = "client:" + clientAuthResponse.getClientId();

        return generateToken(claims, subject, refreshTokenExpirationMs);
    }

    /**
     * Metodo común para generar tokens con tiempo de expiración específico
     */
    private String generateToken(Map<String, Object> claims, String subject, int expirationMs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Refresca los tokens usando un refresh token válido
     */
    public TokenPair refreshTokens(String refreshToken) {
        try {
            // Validar el refresh token
            Claims claims = getClaimsFromToken(refreshToken);

            // Verificar que es un refresh token
            String tokenType = claims.get(Constants.TOKEN_TYPE, String.class);
            if (!Constants.REFRESH.equals(tokenType)) {
                throw new RuntimeException("Token is not a refresh token");
            }

            String userType = claims.get(Constants.USER_TYPE, String.class);

            // Generar nuevos tokens según el tipo de usuario
            if (Constants.AGENT.equals(userType)) {
                return refreshAgentTokens(claims);
            } else if (Constants.CLIENT.equals(userType)) {
                return refreshClientTokens(claims);
            } else {
                throw new RuntimeException("Invalid user type");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh token", e);
        }
    }

    private TokenPair refreshAgentTokens(Claims refreshClaims) {
        Integer documentType = refreshClaims.get(Constants.DOCUMENT_TYPE, Integer.class);
        String documentNumber = refreshClaims.get(Constants.DOCUMENT_NUMBER, String.class);

        // Crear un objeto AgentAuthResponse mínimo con la información del refresh token
        AgentAuthResponse agentData = new AgentAuthResponse();
        agentData.setDocumentType(documentType);
        agentData.setDocumentNumber(documentNumber);
        agentData.setAuthenticated(true);

        // Idealmente, consultarías la base de datos para obtener información actualizada
        // Aquí simplificamos asumiendo que podemos usar la información del token
        List<String> roles = getRolesFromToken(refreshClaims);
        agentData.setRoles(roles);

        // Generar nuevos tokens
        return generateAgentTokens(agentData);
    }

    private TokenPair refreshClientTokens(Claims refreshClaims) {
        Integer clientId = refreshClaims.get(Constants.CLIENT_ID, Integer.class);
        String documentNumber = refreshClaims.get(Constants.DOCUMENT_NUMBER, String.class);

        // Crear un objeto ClientAuthResponse mínimo con la información del refresh token
        ClientAuthResponse clientData = new ClientAuthResponse();
        clientData.setClientId(clientId);
        clientData.setDocumentNumber(documentNumber);
        clientData.setAuthenticated(true);

        // Idealmente, consultarías la base de datos para obtener información actualizada
        // Aquí simplificamos asumiendo que podemos usar la información del token
        List<String> roles = getRolesFromToken(refreshClaims);
        clientData.setRoles(roles);

        // Generar nuevos tokens
        return generateClientTokens(clientData);
    }

    @SuppressWarnings("unchecked")
    private List<String> getRolesFromToken(Claims claims) {
        return claims.get(Constants.ROLES, List.class);
    }

    /**
     * Obtiene el tiempo de expiración del token en segundos
     */
    public Long getTokenExpirationInSeconds(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return (expiration.getTime() - System.currentTimeMillis()) / 1000;
        } catch (Exception e) {
            return 0L;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
                 | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getUserTypeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(Constants.USER_TYPE, String.class);
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Integer getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String userType = claims.get(Constants.USER_TYPE, String.class);

        if (Constants.AGENT.equals(userType)) {
            return claims.get("agentId", Integer.class);
        } else if (Constants.CLIENT.equals(userType)) {
            return claims.get(Constants.CLIENT_ID, Integer.class);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (List<String>) claims.get(Constants.ROLES);
    }
}
