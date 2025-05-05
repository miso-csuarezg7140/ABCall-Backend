package com.abcall.auth.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgentAuthRequest {

    @JsonProperty("tipoDocumento")
    private String documentType;

    @JsonProperty("numeroDocumento")
    private String documentNumber;

    @JsonProperty("contrasena")
    private String password;
}
