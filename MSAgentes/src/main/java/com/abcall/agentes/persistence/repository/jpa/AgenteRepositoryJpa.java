package com.abcall.agentes.persistence.repository.jpa;

import com.abcall.agentes.persistence.entity.Agente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenteRepositoryJpa extends JpaRepository<Agente, Long> {
}
