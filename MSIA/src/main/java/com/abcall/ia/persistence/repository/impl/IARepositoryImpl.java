package com.abcall.ia.persistence.repository.impl;

import com.abcall.ia.persistence.repository.IARepository;
import com.abcall.ia.persistence.repository.jpa.IARepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IARepositoryImpl implements IARepository {

    private final IARepositoryJpa iaRepositoryJpa;
}
