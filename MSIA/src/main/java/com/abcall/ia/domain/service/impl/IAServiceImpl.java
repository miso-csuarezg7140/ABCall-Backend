package com.abcall.ia.domain.service.impl;

import com.abcall.ia.domain.service.IAService;
import com.abcall.ia.persistence.repository.IARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IAServiceImpl implements IAService {

    private final IARepository iaRepository;
}
