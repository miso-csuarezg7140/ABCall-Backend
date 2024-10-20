package com.abcall.incidentes.persistence.repository.jpa;

import com.abcall.incidentes.persistence.entity.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenteRepositoryJpa extends JpaRepository<Incidente, Long> {
}
