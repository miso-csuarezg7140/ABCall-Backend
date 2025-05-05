package com.abcall.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Request para refrescar el token")
public class TokenRefreshRequest {

    @Schema(description = "Token de actualización")
    @NotBlank(message = "El campo token no cumple las validaciones.")
    private String token;
}
