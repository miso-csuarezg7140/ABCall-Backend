package com.abcall.clientes.domain.dto.request;

import com.abcall.clientes.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request para autenticar un cliente")
public class ClientAuthRequest {

    @Schema(description = "Tipo de documento del cliente")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_CLIENTE, message = "El campo numeroDocumento no cumple las validaciones.")
    @NotBlank(message = "El campo numeroDocumento no cumple las validaciones.")
    @JsonProperty("numeroDocumento")
    private String documentNumber;

    @Schema(description = "Contraseña del cliente")
    @NotBlank(message = "El campo contrasena no cumple las validaciones.")
    @JsonProperty("contrasena")
    private String password;
}
