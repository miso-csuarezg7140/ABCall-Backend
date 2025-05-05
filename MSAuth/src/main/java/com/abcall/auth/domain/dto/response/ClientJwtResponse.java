package com.abcall.auth.domain.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ClientJwtResponse extends JwtResponse {
    private Integer clientId;
    private String documentNumber;
    private String socialReason;
    private String email;

    public ClientJwtResponse(String token, String refreshToken, Long expiresIn, List<String> roles,
                             Integer clientId, String documentNumber, String socialReason, String email) {
        super(token, refreshToken, expiresIn, roles, "client");
        this.clientId = clientId;
        this.documentNumber = documentNumber;
        this.socialReason = socialReason;
        this.email = email;
    }
}
