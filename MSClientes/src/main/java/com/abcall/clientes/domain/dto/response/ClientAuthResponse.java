package com.abcall.clientes.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthResponse {

    private Integer clientId;
    private Long documentNumber;
    private List<String> roles;
    private boolean authenticated;
    private String socialReason;
    private String email;
    private LocalDateTime lastLogin;
}
