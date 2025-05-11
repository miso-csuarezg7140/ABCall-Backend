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
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tipo_doc_usuario", nullable = false)
    private Integer userDocumentType;

    @Column(name = "num_doc_usuario", nullable = false)
    private String userDocumentNumber;

    @Column(name = "num_doc_cliente", nullable = false)
    private Long clientDocumentNumber;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "solucionado", nullable = false)
    private Boolean solved;

    @Column(name = "solucion_id")
    private Integer idSolution;

    @Column(name = "solucionado_por")
    private String solvedBy;

    @Column(name = "fecha_solucion")
    private LocalDateTime solvedDate;

    @Column(name = "estado", nullable = false)
    private String status;

    @Column(name = "creado_por", nullable = false)
    private String createdBy;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modificado_por")
    private String modifiedBy;

    @Column(name = "fecha_modificacion")
    private LocalDateTime modifiedDate;
}
