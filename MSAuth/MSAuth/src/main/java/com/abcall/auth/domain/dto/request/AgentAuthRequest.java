package com.abcall.auth.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentAuthRequest {

    private String username;
    private String password;
}
