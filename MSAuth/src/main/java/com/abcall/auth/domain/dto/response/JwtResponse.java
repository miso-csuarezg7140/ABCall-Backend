package com.abcall.auth.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long expiresIn;
    private List<String> roles;
    private String userType;

    protected JwtResponse(String token, String refreshToken, Long expiresIn, List<String> roles, String userType) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.roles = roles;
        this.userType = userType;
    }
}