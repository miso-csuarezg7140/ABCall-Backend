package com.abcall.agentes.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentAuthResponse {

    private Integer documentType;
    private String documentNumber;
    private List<String> roles;
    private boolean authenticated;
    private String names;
    private String surnames;
}
