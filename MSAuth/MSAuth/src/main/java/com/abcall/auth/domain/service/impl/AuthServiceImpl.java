package com.abcall.auth.domain.service.impl;

import com.abcall.auth.domain.dto.request.AgentAuthRequest;
import com.abcall.auth.domain.dto.request.ClientAuthRequest;
import com.abcall.auth.domain.dto.response.AgentAuthResponse;
import com.abcall.auth.domain.dto.response.ClientAuthResponse;
import com.abcall.auth.domain.dto.response.JwtResponse;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.domain.service.IAuthService;
import com.abcall.auth.security.JwtTokenProvider;
import com.abcall.auth.util.ApiUtils;
import com.abcall.auth.util.enums.HttpResponseCodes;
import com.abcall.auth.util.enums.HttpResponseMessages;
import com.abcall.auth.web.external.IAgentService;
import com.abcall.auth.web.external.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {

    private final IAgentService agentService;
    private final IClientService clientService;
    private final JwtTokenProvider tokenProvider;
    private final ApiUtils apiUtils;

    /**
     * Authenticates a user based on the provided username, password, and user type.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param userType the type of the user (either "agent" or other)
     * @return a ResponseServiceDto containing the authentication result and JWT token if successful,
     * or an unauthorized response if authentication fails
     */
    @Override
    public ResponseServiceDto authenticateUser(String username, String password, String userType) {
        if ("agent".equals(userType)) {
            AgentAuthRequest request = new AgentAuthRequest(username, password);
            ResponseEntity<ResponseServiceDto> response = agentService.authenticateAgent(request);

            if (null != response.getBody() && response.getBody().getStatusCode() == HttpResponseCodes.OK.getCode()) {
                AgentAuthResponse agent = (AgentAuthResponse) response.getBody().getData();
                if (agent.isAuthenticated()) {
                    String token = tokenProvider.generateAgentToken(agent.getAgentId(),
                            agent.getUsername(),
                            agent.getRoles());
                    return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                            new JwtResponse(token));
                }
            }
        } else {
            ClientAuthRequest request = new ClientAuthRequest(username, password);
            ResponseEntity<ResponseServiceDto> response = clientService.authenticateClient(request);

            if (null != response.getBody() && response.getBody().getStatusCode() == HttpResponseCodes.OK.getCode()) {
                ClientAuthResponse client = (ClientAuthResponse) response.getBody().getData();
                if (client.isAuthenticated()) {
                    String token = tokenProvider.generateClientToken(client.getClientId(),
                            client.getUsername(),
                            client.getRoles());
                    return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                            new JwtResponse(token));
                }
            }
        }

        return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(), "Credenciales inválidas");
    }
}
