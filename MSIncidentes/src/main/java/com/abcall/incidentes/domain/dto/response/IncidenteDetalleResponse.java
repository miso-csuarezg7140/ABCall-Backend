package com.abcall.incidentes.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncidenteDetalleResponse {

    private Integer id;
    private String tipoDocumentoUsuario;
    private Long numDocumentoUsuario;
    private Long numDocumentoCliente;
    private String descripcion;
    private Boolean solucionado;
    private Integer solucionId;
    private String solucionadoPor;
    private LocalDateTime fechaSolucion;
    private String estado;
    private String creadoPor;
    private LocalDateTime fechaCreacion;
    private String modificadoPor;
    private LocalDateTime fechaModificacion;
}
