package com.abcall.clientes.domain.dto.request;

import com.abcall.clientes.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para registrar un cliente")
public class ClientRegisterRequest {

    @Schema(description = "Tipo de documento del cliente")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_CLIENTE, message = "El campo numeroDocumento no cumple las validaciones.")
    @NotBlank(message = "El campo numeroDocumento no cumple las validaciones.")
    @JsonProperty("numeroDocumento")
    private String documentNumber;

    @Schema(description = "Nombre del cliente / Razón social")
    @Size(max = 50, message = "El campo razonSocial no cumple las validaciones.")
    @NotBlank(message = "El campo razonSocial no cumple las validaciones.")
    @JsonProperty("razonSocial")
    private String socialReason;

    @Schema(description = "Email del cliente")
    @Size(max = 50, message = "El campo nombre no cumple las validaciones.")
    @NotBlank(message = "El campo correo no cumple las validaciones.")
    @Email(message = "El campo correo no cumple las validaciones.")
    @JsonProperty("correo")
    private String email;

    @Schema(description = "Contraseña del cliente")
    @Pattern(regexp = Constants.VALIDACION_CONTRASENA, message = "El campo contrasena no cumple las validaciones.")
    @NotBlank(message = "El campo contrasena no cumple las validaciones.")
    @JsonProperty("contrasena")
    private String password;
}
