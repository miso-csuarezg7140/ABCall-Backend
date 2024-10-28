package com.abcall.clientes.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.abcall.clientes.util.Constant.ESTADO_DEFAULT;
import static com.abcall.clientes.util.Constant.HOY;
import static com.abcall.clientes.util.Constant.VALIDACION_CONTRASENA;
import static com.abcall.clientes.util.Constant.VALIDACION_NUMERICO;

@Getter
@Setter
public class ClienteDto {

    @NotNull(message = "El campo numeroDocumento no puede ser nulo.")
    @Pattern(regexp = VALIDACION_NUMERICO, message = "El campo numeroDocumento no cumple con el formato requerido")
    private Long numeroDocumento;

    @NotBlank(message = "El campo razonSocial no puede ser nulo.")
    private String razonSocial;


    @NotBlank(message = "El campo contrasena no puede ser nulo.")
    @Pattern(regexp = VALIDACION_CONTRASENA, message = "La contrasena no cumple con el formato requerido.")
    private String contrasena;

    @NotBlank(message = "El campo plan no puede ser nulo.")
    private String plan;

    @NotBlank(message = "El campo creadoPor no puede ser nulo.")
    private String creadoPor;

    private LocalDateTime fechaCreacion = HOY;

    private String estado = ESTADO_DEFAULT;
}
