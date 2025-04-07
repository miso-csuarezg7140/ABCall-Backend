package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IClientRepositoryJpa extends JpaRepository<Client, Integer> {

    @Query("SELECT c FROM Client c WHERE c.documentNumber = :documentNumber")
    Optional<Client> findByDocumentNumber(@Param("documentNumber") Long documentNumber);
}
