package com.abcall.agentes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentAuthenticationInfo {

    private String subject;
    private Integer documentType;
    private String documentNumber;
    private String names;
    private String surnames;
    private List<String> roles;
}