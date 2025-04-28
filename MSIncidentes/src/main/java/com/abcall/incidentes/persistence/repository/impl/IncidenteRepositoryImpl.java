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
        return incidenteRepositoryJpa.obtenerPorUsuario(tipoDocumentoUsuario, numDocumentoUsuario).orElse(null);
    }

    @Override
    public Incidente obtenerPorId(Integer idIncidente) {
        return incidenteRepositoryJpa.findById(idIncidente).orElse(null);
    }

    @Override
    public Incidente crear(Incidente incidente) {
        return incidenteRepositoryJpa.save(incidente);
    }

    @Override
    public Incidente actualizar(Incidente incidente) {
        boolean existeIncidente =  incidenteRepositoryJpa.existsById(incidente.getId());
        return existeIncidente ? incidenteRepositoryJpa.save(incidente) : null;
    }
}
