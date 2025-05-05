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
public class ClientAuthResponse {

    private Integer clientId;
    private String documentNumber;
    private List<String> roles;
    private boolean authenticated;
    private String socialReason;
    private String email;
}
