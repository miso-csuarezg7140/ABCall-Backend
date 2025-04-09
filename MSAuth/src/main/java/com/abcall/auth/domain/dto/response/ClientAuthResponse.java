package com.abcall.auth.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthResponse {

    private Integer clientId;
    private String username;
    private List<String> roles;
    private boolean authenticated;
    @JsonIgnore
    private String socialReason;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private LocalDateTime lastLogin;
}
