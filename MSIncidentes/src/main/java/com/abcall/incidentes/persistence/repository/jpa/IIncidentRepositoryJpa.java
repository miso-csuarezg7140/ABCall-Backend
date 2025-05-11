package com.abcall.incidentes.persistence.repository.jpa;

import com.abcall.incidentes.persistence.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface IIncidentRepositoryJpa extends JpaRepository<Incident, Integer> {

    @Query("SELECT i FROM Incident i " +
            "WHERE (i.clientDocumentNumber = :clientDocumentNumber OR :clientDocumentNumber IS NULL) " +
            "AND (i.userDocumentType = :userDocumentType OR :userDocumentType IS NULL) " +
            "AND (i.userDocumentNumber = :userDocumentNumber OR :userDocumentNumber IS NULL) " +
            "AND (i.status = :status OR :status = 'TODOS') " +
            "AND ((:withDates = true AND i.createdDate BETWEEN :startDate AND :endDate) OR :withDates = false)")
    Page<Incident> getIncidents(@Param("clientDocumentNumber") Long clientDocumentNumber,
                                @Param("userDocumentType") Integer userDocumentType,
                                @Param("userDocumentNumber") String userDocumentNumber,
                                @Param("status") String status,
                                @Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate,
                                @Param("withDates") Boolean withDates,
                                @Param("pageable")
                                Pageable pageable);
}
