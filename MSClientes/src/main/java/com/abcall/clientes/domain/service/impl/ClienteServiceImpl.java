package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.domain.service.ClienteService;
import com.abcall.clientes.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
}
