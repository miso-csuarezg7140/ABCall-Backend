package com.abcall.clientes.persistence.repository.jpa;

import com.abcall.clientes.persistence.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositoryJpa extends JpaRepository<Cliente, Long> {
}
