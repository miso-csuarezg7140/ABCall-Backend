package com.abcall.clientes.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientAuthenticationInfo {

    private Long clientId;
    private String username;
    private List<String> roles;
}
