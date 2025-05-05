package com.abcall.clientes.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClientAuthenticationInfo {

    private String subject;
    private Integer clientId;
    private Long documentNumber;
    private String socialReason;
    private String email;
    private List<String> roles;
}
