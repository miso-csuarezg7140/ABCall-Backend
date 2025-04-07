package com.abcall.auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthResponse {

    private Integer clientId;
    private String username;
    private List<String> roles;
    private boolean authenticated;
}
