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
public class IncidenteRequest {

    private Integer id;

    @NotBlank(message = "El campo tipoDocumentoUsuario no puede ser nulo.")
    private String tipoDocumentoUsuario;

    @NotNull(message = "El campo numDocumentoUsuario no puede ser nulo.")
    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo numDocumentoUsuario debe ser numérico.")
    private Long numDocumentoUsuario;

    @NotNull(message = "El campo numDocumentoCliente no puede ser nulo.")
    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo numDocumentoCliente debe ser numérico.")
    private Long numDocumentoCliente;

    @NotBlank(message = "El campo descripcion no puede ser nulo.")
    private String descripcion;

    private Boolean solucionado = false;
    private Integer solucionId;
    private String solucionadoPor;
    private LocalDateTime fechaSolucion;
    private String estado = Constants.ESTADO_DEFAULT;

    private String creadoPor = Constants.ESTADO_DEFAULT;

    private LocalDateTime fechaCreacion = Constants.HOY;
    private String modificadoPor;

    private LocalDateTime fechaModificacion;
}
