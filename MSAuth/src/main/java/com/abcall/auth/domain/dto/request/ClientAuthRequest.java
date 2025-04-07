package com.abcall.auth.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientAuthRequest {

    private String username;
    private String password;
}
