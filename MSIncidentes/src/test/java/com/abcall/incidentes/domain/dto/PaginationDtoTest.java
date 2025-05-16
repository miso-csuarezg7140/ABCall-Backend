package com.abcall.incidentes.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaginationDtoTest {

    @Test
    void creationSucceedsWithValidValues() {
        PaginationDto pagination = new PaginationDto(1, 10, 5, 50L);

        assertEquals(1, pagination.getPage());
        assertEquals(10, pagination.getPageSize());
        assertEquals(5, pagination.getTotalPages());
        assertEquals(50L, pagination.getTotalElements());
    }
}