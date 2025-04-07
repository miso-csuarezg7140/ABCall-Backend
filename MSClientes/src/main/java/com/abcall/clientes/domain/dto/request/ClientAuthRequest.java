package com.abcall.clientes.domain.dto.request;

import com.abcall.clientes.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthRequest {

    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo username no cumple las validaciones.")
    @NotBlank(message = "El campo username no cumple las validaciones.")
    private String username;

    @NotBlank(message = "El campo password no cumple las validaciones.")
    private String password;
}
