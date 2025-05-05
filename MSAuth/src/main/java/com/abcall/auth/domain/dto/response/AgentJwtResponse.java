package com.abcall.auth.domain.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AgentJwtResponse extends JwtResponse {
    private String documentNumber;
    private Integer documentType;
    private String names;
    private String surnames;

    public AgentJwtResponse(String token, String refreshToken, Long expiresIn, List<String> roles,
                            String documentNumber, Integer documentType, String names, String surnames) {
        super(token, refreshToken, expiresIn, roles, "agent");
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.names = names;
        this.surnames = surnames;
    }
}
