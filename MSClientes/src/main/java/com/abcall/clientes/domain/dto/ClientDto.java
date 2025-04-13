package com.abcall.clientes.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.abcall.clientes.util.Constants.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Integer clientId;

    @JsonProperty("numeroDocumento")
    @NotNull(message = "El campo numeroDocumento cumple las validaciones.")
    @Pattern(regexp = VALIDACION_NUMERICO, message = "El campo numeroDocumento cumple las validaciones.")
    private Long documentNumber;

    @JsonProperty("contrasena")
    @NotBlank(message = "El campo contrasena no cumple las validaciones.")
    private String password;

    @JsonProperty("razonSocial")
    @NotBlank(message = "El campo razonSocial cumple las validacioneso.")
    private String socialReason;

    @JsonProperty("correo")
    @NotBlank(message = "El campo email")
    private String email;

    @NotBlank(message = "El campo plan no cumple las validaciones.")
    private String plan;

    private LocalDateTime createdDate = HOY;

    private LocalDateTime updatedDate;

    private LocalDateTime lastLogin;

    private Character status = ESTADO_DEFAULT;
}
