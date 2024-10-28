package com.abcall.incidentes.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.abcall.incidentes.util.Constant.ESTADO_DEFAULT;
import static com.abcall.incidentes.util.Constant.HOY;
import static com.abcall.incidentes.util.Constant.VALIDACION_NUMERICO;

@Getter
@Setter
public class IncidenteDto {

    private Integer id;

    @NotBlank(message = "El campo tipoDocumentoUsuario no puede ser nulo.")
    private String tipoDocumentoUsuario;

    @NotNull(message = "El campo numDocumentoUsuario no puede ser nulo.")
    @Pattern(regexp = VALIDACION_NUMERICO, message = "El campo numDocumentoUsuario debe ser numérico.")
    private Long numDocumentoUsuario;

    @NotNull(message = "El campo numDocumentoCliente no puede ser nulo.")
    @Pattern(regexp = VALIDACION_NUMERICO, message = "El campo numDocumentoCliente debe ser numérico.")
    private Long numDocumentoCliente;

    @NotBlank(message = "El campo descripcion no puede ser nulo.")
    private String descripcion;

    private Boolean solucionado = false;
    private Integer solucionId;
    private String solucionadoPor;
    private LocalDateTime fechaSolucion;
    private String estado = ESTADO_DEFAULT;

    @NotBlank(message = "El campo creadoPor no puede ser nulo.")
    private String creadoPor;

    private LocalDateTime fechaCreacion = HOY;
    private String modificadoPor;

    private LocalDateTime fechaModificacion;
}
