package com.abcall.auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AgentAuthResponse {

    private Integer documentType;
    private String documentNumber;
    private List<String> roles;
    private boolean authenticated;
    private String names;
    private String surnames;
}
