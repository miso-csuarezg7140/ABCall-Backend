package com.abcall.reporteria.domain.service.impl;

import com.abcall.reporteria.domain.service.ReporteriaService;
import com.abcall.reporteria.persistence.repository.ReporteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporteriaServiceImpl implements ReporteriaService {

    private final ReporteriaRepository reporteriaRepository;
}
