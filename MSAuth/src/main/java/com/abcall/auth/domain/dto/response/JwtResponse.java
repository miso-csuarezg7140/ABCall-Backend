package com.abcall.auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Integer id;
    private String username;
    private String userType;
    private List<String> roles;

    public JwtResponse(String token) {
        this.token = token;
    }
}
