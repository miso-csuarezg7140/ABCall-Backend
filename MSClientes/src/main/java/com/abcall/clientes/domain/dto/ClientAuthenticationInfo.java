package com.abcall.clientes.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClientAuthenticationInfo {

    private Long clientId;
    private String username;
    private List<String> roles;
}
