package com.abcall.incidentes.persistence.repository.jpa;

import com.abcall.incidentes.persistence.entity.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidenteRepositoryJpa extends JpaRepository<Incidente, Integer> {

    @Query("SELECT i FROM Incidente i " +
            "WHERE i.tipoDocumentoUsuario = :tipoDocumentoUsuario AND i.numDocumentoUsuario = :numDocumentoUsuario")
    List<Incidente> obtenerPorUsuario(@Param("tipoDocumentoUsuario") String tipoDocumentoUsuario,
                                      @Param("numDocumentoUsuario") Long numDocumentoUsuario);
}
