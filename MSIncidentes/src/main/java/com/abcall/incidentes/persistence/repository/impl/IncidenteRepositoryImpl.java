package com.abcall.incidentes.persistence.repository.impl;

import com.abcall.incidentes.persistence.entity.Incidente;
import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import com.abcall.incidentes.persistence.repository.jpa.IncidenteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncidenteRepositoryImpl implements IncidenteRepository {

    private final IncidenteRepositoryJpa incidenteRepositoryJpa;

    @Override
    public List<Incidente> obtenerPorUsuario(String tipoDocumentoUsuario, Long numDocumentoUsuario) {
        return incidenteRepositoryJpa.obtenerPorUsuario(tipoDocumentoUsuario, numDocumentoUsuario);
    }

    @Override
    public Incidente crear(Incidente incidente) {
        return incidenteRepositoryJpa.save(incidente);
    }
}
