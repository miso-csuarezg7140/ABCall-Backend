package com.abcall.incidentes.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "incidente")
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tipo_doc_usuario", nullable = false)
    private String tipoDocumentoUsuario;

    @Column(name = "num_doc_usuario", nullable = false)
    private Long numDocumentoUsuario;

    @Column(name = "num_doc_cliente", nullable = false)
    private Long numDocumentoCliente;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "solucionado", nullable = false)
    private Boolean solucionado;

    @Column(name = "solucion_id")
    private Integer solucionId;

    @Column(name = "solucionado_por")
    private String solucionadoPor;

    @Column(name = "fecha_solucion")
    private LocalDateTime fechaSolucion;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "modificado_por")
    private String modificadoPor;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
}
