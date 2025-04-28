package com.abcall.incidentes.persistence.repository;

import com.abcall.incidentes.persistence.entity.Incidente;

import java.util.List;

public interface IncidenteRepository {

    List<Incidente> obtenerPorUsuario(String tipoDocumentoUsuario, Long numDocumentoUsuario);

    Incidente obtenerPorId(Integer idIncidente);

    Incidente crear(Incidente incidente);

    Incidente actualizar(Incidente incidente);
}
