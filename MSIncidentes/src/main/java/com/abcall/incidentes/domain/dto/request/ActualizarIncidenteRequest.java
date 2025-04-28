package com.abcall.incidentes.domain.dto.request;

import com.abcall.incidentes.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActualizarIncidenteRequest {

    @NotBlank(message = "El campo idIncidente no puede ser nulo.")
    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo idIncidente debe ser numérico.")
    private String idIncidente;

    @NotBlank(message = "El campo tipoDocumentoUsuario no puede ser nulo.")
    private String tipoDocumentoUsuario;

    @NotBlank(message = "El campo numDocumentoUsuario no puede ser nulo.")
    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo numDocumentoUsuario debe ser numérico.")
    private String numDocumentoUsuario;

    @NotBlank(message = "El campo numDocumentoCliente no puede ser nulo.")
    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo numDocumentoCliente debe ser numérico.")
    private String numDocumentoCliente;

    @NotNull(message = "El campo solucionado no puede ser nulo.")
    private Boolean solucionado;

    @NotBlank(message = "El campo estado no puede ser nulo.")
    private String estado;

    @NotBlank(message = "El campo modificadoPor no puede ser nulo.")
    private String modificadoPor;

    private LocalDateTime fechaModificacion = Constants.HOY;
}