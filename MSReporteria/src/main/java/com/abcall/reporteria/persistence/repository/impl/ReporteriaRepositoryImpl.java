package com.abcall.reporteria.persistence.repository.impl;

import com.abcall.reporteria.persistence.repository.ReporteriaRepository;
import com.abcall.reporteria.persistence.repository.jpa.ReporteriaRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReporteriaRepositoryImpl implements ReporteriaRepository {

    private final ReporteriaRepositoryJpa reporteriaRepositoryJpa;
}
