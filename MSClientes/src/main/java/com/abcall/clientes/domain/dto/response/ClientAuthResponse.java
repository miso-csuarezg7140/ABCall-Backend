package com.abcall.clientes.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthResponse {

    private Integer clientId;
    private Long documentNumber;
    private boolean authenticated;
    private String socialReason;
    private String email;
    private LocalDateTime lastLogin;
}
