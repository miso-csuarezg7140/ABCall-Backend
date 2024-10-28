package com.abcall.incidentes.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.abcall.incidentes.util.Constant.HOY;

@Getter
@Setter
public class IncidenteDto {

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
    private LocalDateTime fechaCreacion = HOY;
    private String modificadoPor;
    private LocalDateTime fechaModificacion;
}
