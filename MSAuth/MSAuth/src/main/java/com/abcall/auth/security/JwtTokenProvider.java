package com.abcall.auth.security;

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

    private final SecretKey key;
    private final int jwtExpirationMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret,
                            @Value("${jwt.access.expiration.ms}") int jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /**
     * Generates a JWT token for an agent.
     *
     * @param agentId  the ID of the agent
     * @param username the username of the agent
     * @param roles    the roles assigned to the agent
     * @return the generated JWT token
     */
    public String generateAgentToken(Integer agentId, String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USER_TYPE, "agent");
        claims.put("agentId", agentId);
        claims.put(Constants.ROLES, roles);

        return generateToken(claims, username);
    }

    /**
     * Generates a JWT token for a client.
     *
     * @param clientId the ID of the client
     * @param username the username of the client
     * @param roles    the roles assigned to the client
     * @return the generated JWT token
     */
    public String generateClientToken(Integer clientId, String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.USER_TYPE, "client");
        claims.put("clientId", clientId);
        claims.put(Constants.ROLES, roles);

        return generateToken(claims, username);
    }

    /**
     * Generates a JWT token with the specified claims and subject.
     *
     * @param claims  a map containing the claims to be included in the token
     * @param subject the subject (typically the username) to be included in the token
     * @return the generated JWT token as a String
     */
    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
                 | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extracts the claims from the given JWT token.
     *
     * @param token the JWT token from which to extract the claims
     * @return the claims extracted from the token
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the token
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Extracts the user type from the given JWT token.
     *
     * @param token the JWT token from which to extract the user type
     * @return the user type extracted from the token
     */
    public String getUserTypeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(Constants.USER_TYPE, String.class);
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * Refreshes the given JWT token by updating its issued at and expiration dates.
     *
     * @param token the JWT token to refresh
     * @return the refreshed JWT token, or null if an error occurs
     */
    public String refreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            claims.setIssuedAt(new Date());
            claims.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs));

            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(key)
                    .compact();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts the user ID from the given JWT token.
     *
     * @param token the JWT token from which to extract the user ID
     * @return the user ID extracted from the token, or null if the user type is not recognized
     */
    public Integer getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String userType = claims.get(Constants.USER_TYPE, String.class);

        if ("agent".equals(userType)) {
            return claims.get("agentId", Integer.class);
        } else if ("client".equals(userType)) {
            return claims.get("clientId", Integer.class);
        }

        return null;
    }

    /**
     * Extracts the roles from the given JWT token.
     *
     * @param token the JWT token from which to extract the roles
     * @return the roles extracted from the token as a List of Strings
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (List<String>) claims.get(Constants.ROLES);
    }
}
