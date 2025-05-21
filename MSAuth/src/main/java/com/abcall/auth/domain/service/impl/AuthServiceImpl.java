package com.abcall.auth.domain.service.impl;

import com.abcall.auth.domain.dto.request.AgentAuthRequest;
import com.abcall.auth.domain.dto.request.ClientAuthRequest;
import com.abcall.auth.domain.dto.request.LoginRequest;
import com.abcall.auth.domain.dto.request.TokenRefreshRequest;
import com.abcall.auth.domain.dto.response.AgentAuthResponse;
import com.abcall.auth.domain.dto.response.AgentJwtResponse;
import com.abcall.auth.domain.dto.response.ClientAuthResponse;
import com.abcall.auth.domain.dto.response.ClientJwtResponse;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.domain.service.IAuthService;
import com.abcall.auth.security.JwtTokenProvider;
import com.abcall.auth.security.TokenPair;
import com.abcall.auth.util.ApiUtils;
import com.abcall.auth.util.enums.HttpResponseCodes;
import com.abcall.auth.util.enums.HttpResponseMessages;
import com.abcall.auth.web.external.IAgentService;
import com.abcall.auth.web.external.IClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {

    private final IAgentService agentService;
    private final IClientService clientService;
    private final JwtTokenProvider tokenProvider;
    private final ApiUtils apiUtils;

    /**
     * Authenticates a user based on the provided login request.
     * <p>
     * This method determines the type of user (agent or client) from the login request
     * and delegates the authentication process to the appropriate method.
     * If an exception occurs during the process, it returns an internal server error response.
     *
     * @param loginRequest the login request containing user credentials and type
     * @return a ResponseServiceDto containing the authentication result:
     * - If successful, includes a JWT response with access and refresh tokens.
     * - If authentication fails, returns an error response with the appropriate status and message.
     */
    @Override
    public ResponseServiceDto authenticateUser(LoginRequest loginRequest) {
        try {
            if ("agente".equals(loginRequest.getUserType()))
                return authenticateAgent(loginRequest);
            else
                return authenticateClient(loginRequest);
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    /**
     * Authenticates an agent based on the provided login request.
     *
     * @param loginRequest the login request containing the agent's document type, document number, and password
     * @return a ResponseServiceDto containing the authentication result:
     * - If successful, includes a JWT response with access and refresh tokens.
     * - If authentication fails, returns an unauthorized response with an error message.
     */
    private ResponseServiceDto authenticateAgent(LoginRequest loginRequest) {
        AgentAuthRequest request = new AgentAuthRequest(
                loginRequest.getDocumentType(),
                loginRequest.getDocumentNumber(),
                loginRequest.getPassword()
        );

        ResponseEntity<ResponseServiceDto> response = agentService.authenticateAgent(request);

        if (null != response.getBody() && response.getBody().getStatusCode() == HttpResponseCodes.OK.getCode()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            AgentAuthResponse agentAuthResponse = mapper.convertValue(
                    response.getBody().getData(), AgentAuthResponse.class);

            if (agentAuthResponse.isAuthenticated()) {
                TokenPair tokenPair = tokenProvider.generateAgentTokens(agentAuthResponse);
                AgentJwtResponse jwtResponse = new AgentJwtResponse(
                        tokenPair.getAccessToken(),
                        tokenPair.getRefreshToken(),
                        tokenProvider.getTokenExpirationInSeconds(tokenPair.getAccessToken()),
                        agentAuthResponse.getRoles(),
                        agentAuthResponse.getDocumentNumber(),
                        agentAuthResponse.getDocumentType(),
                        agentAuthResponse.getNames(),
                        agentAuthResponse.getSurnames()
                );

                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), jwtResponse);
            }
        }

        return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(), new HashMap<>());
    }

    /**
     * Authenticates a client based on the provided login request.
     * <p>
     * This method sends the client's credentials to the client service for authentication.
     * If the authentication is successful, it generates a JWT response containing access
     * and refresh tokens, along with client details. If authentication fails, it returns
     * an unauthorized response with an error message.
     *
     * @param loginRequest the login request containing the client's document number and password
     * @return a ResponseServiceDto containing the authentication result:
     * - If successful, includes a JWT response with access and refresh tokens.
     * - If authentication fails, returns an unauthorized response with an error message.
     */
    private ResponseServiceDto authenticateClient(LoginRequest loginRequest) {
        ClientAuthRequest request = new ClientAuthRequest(
                loginRequest.getDocumentNumber(),
                loginRequest.getPassword()
        );

        ResponseEntity<ResponseServiceDto> response = clientService.authenticateClient(request);

        if (null != response.getBody() && response.getBody().getStatusCode() == HttpResponseCodes.OK.getCode()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            ClientAuthResponse clientAuthResponse = mapper.convertValue(
                    response.getBody().getData(), ClientAuthResponse.class);

            if (clientAuthResponse.isAuthenticated()) {
                TokenPair tokenPair = tokenProvider.generateClientTokens(clientAuthResponse);
                ClientJwtResponse jwtResponse = new ClientJwtResponse(
                        tokenPair.getAccessToken(),
                        tokenPair.getRefreshToken(),
                        tokenProvider.getTokenExpirationInSeconds(tokenPair.getAccessToken()),
                        clientAuthResponse.getRoles(),
                        clientAuthResponse.getClientId(),
                        clientAuthResponse.getDocumentNumber(),
                        clientAuthResponse.getSocialReason(),
                        clientAuthResponse.getEmail()
                );

                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), jwtResponse);
            }
        }

        return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(), new HashMap<>());
    }

    /**
     * Refreshes the authentication tokens for a user based on the provided refresh token.
     *
     * @param request the token refresh request containing the current refresh token
     * @return a ResponseServiceDto containing the new access and refresh tokens,03
     * along with user details if the operation is successful, or an error message if not
     */
    public ResponseServiceDto refreshToken(TokenRefreshRequest request) {
        try {
            TokenPair tokenPair = tokenProvider.refreshTokens(request.getToken());
            String userType = tokenProvider.getUserTypeFromToken(tokenPair.getAccessToken());

            if ("agent".equals(userType)) {
                Claims claims = tokenProvider.getClaimsFromToken(tokenPair.getAccessToken());

                AgentJwtResponse response = new AgentJwtResponse(
                        tokenPair.getAccessToken(),
                        tokenPair.getRefreshToken(),
                        tokenProvider.getTokenExpirationInSeconds(tokenPair.getAccessToken()),
                        tokenProvider.getRolesFromToken(tokenPair.getAccessToken()),
                        claims.get("documentNumber", String.class),
                        claims.get("documentType", Integer.class),
                        claims.get("names", String.class),
                        claims.get("surnames", String.class)
                );

                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), response);

            } else if ("client".equals(userType)) {
                Claims claims = tokenProvider.getClaimsFromToken(tokenPair.getAccessToken());

                ClientJwtResponse response = new ClientJwtResponse(
                        tokenPair.getAccessToken(),
                        tokenPair.getRefreshToken(),
                        tokenProvider.getTokenExpirationInSeconds(tokenPair.getAccessToken()),
                        tokenProvider.getRolesFromToken(tokenPair.getAccessToken()),
                        claims.get("clientId", Integer.class),
                        claims.get("documentNumber", String.class),
                        claims.get("socialReason", String.class),
                        claims.get("email", String.class)
                );

                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                        HttpResponseMessages.OK.getMessage(), response);
            } else
                return apiUtils.buildResponse(HttpResponseCodes.BAD_REQUEST.getCode(),
                        HttpResponseMessages.BAD_REQUEST.getMessage(), new HashMap<>());
        } catch (Exception e) {
            return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                    HttpResponseMessages.UNAUTHORIZED.getMessage(), e.getMessage());
        }
    }
}
