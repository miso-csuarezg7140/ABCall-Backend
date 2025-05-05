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

import static com.abcall.clientes.util.Constants.ESTADO_DEFAULT;
import static com.abcall.clientes.util.Constants.HOY;
import static com.abcall.clientes.util.Constants.VALIDACION_DOCUMENTO_CLIENTE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Integer idClient;

    @Pattern(regexp = VALIDACION_DOCUMENTO_CLIENTE, message = "El campo numeroDocumento cumple las validaciones.")
    @NotNull(message = "El campo numeroDocumento cumple las validaciones.")
    @JsonProperty("numeroDocumento")
    private Long documentNumber;

    @NotBlank(message = "El campo contrasena no cumple las validaciones.")
    @JsonProperty("contrasena")
    private String password;

    @NotBlank(message = "El campo razonSocial cumple las validacioneso.")
    @JsonProperty("razonSocial")
    private String socialReason;

    @JsonProperty("correo")
    @NotBlank(message = "El campo email")
    private String email;

    @NotBlank(message = "El campo plan no cumple las validaciones.")
    @JsonProperty("plan")
    private String plan;

    private LocalDateTime createdDate = HOY;

    private LocalDateTime updatedDate;

    private LocalDateTime lastLogin;

    private Character status = ESTADO_DEFAULT;
}
