package com.abcall.ia.persistence.repository.impl;

import com.abcall.ia.persistence.repository.jpa.IARepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IARepositoryImpl {

    private final IARepositoryJpa iaRepositoryJpa;
}
