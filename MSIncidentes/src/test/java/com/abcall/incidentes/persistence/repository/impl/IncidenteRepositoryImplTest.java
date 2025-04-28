package com.abcall.incidentes.persistence.repository.impl;

import com.abcall.incidentes.persistence.entity.Incidente;
import com.abcall.incidentes.persistence.repository.jpa.IncidenteRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidenteRepositoryImplTest {

    @Mock
    private IncidenteRepositoryJpa incidenteRepositoryJpa;

    @InjectMocks
    private IncidenteRepositoryImpl incidenteRepositoryImpl;

    @Test
    void obtenerPorUsuarioReturnsListWhenDataExists() {
        String tipoDocumentoUsuario = "CC";
        Long numDocumentoUsuario = 123456789L;
        List<Incidente> expectedIncidentes = List.of(new Incidente());

        when(incidenteRepositoryJpa.obtenerPorUsuario(tipoDocumentoUsuario, numDocumentoUsuario))
                .thenReturn(Optional.of(expectedIncidentes));

        List<Incidente> result = incidenteRepositoryImpl.obtenerPorUsuario(tipoDocumentoUsuario, numDocumentoUsuario);

        assertNotNull(result);
        assertEquals(expectedIncidentes, result);
    }

    @Test
    void obtenerPorUsuarioReturnsNullWhenNoDataExists() {
        String tipoDocumentoUsuario = "CC";
        Long numDocumentoUsuario = 123456789L;

        when(incidenteRepositoryJpa.obtenerPorUsuario(tipoDocumentoUsuario, numDocumentoUsuario))
                .thenReturn(Optional.empty());

        List<Incidente> result = incidenteRepositoryImpl.obtenerPorUsuario(tipoDocumentoUsuario, numDocumentoUsuario);

        assertNull(result);
    }

    @Test
    void obtenerPorIdReturnsIncidenteWhenIdExists() {
        Integer idIncidente = 1;
        Incidente expectedIncidente = new Incidente();

        when(incidenteRepositoryJpa.findById(idIncidente)).thenReturn(Optional.of(expectedIncidente));

        Incidente result = incidenteRepositoryImpl.obtenerPorId(idIncidente);

        assertNotNull(result);
        assertEquals(expectedIncidente, result);
    }

    @Test
    void obtenerPorIdReturnsNullWhenIdDoesNotExist() {
        Integer idIncidente = 1;

        when(incidenteRepositoryJpa.findById(idIncidente)).thenReturn(Optional.empty());

        Incidente result = incidenteRepositoryImpl.obtenerPorId(idIncidente);

        assertNull(result);
    }

    @Test
    void crearSavesAndReturnsIncidente() {
        Incidente incidenteToSave = new Incidente();
        Incidente savedIncidente = new Incidente();

        when(incidenteRepositoryJpa.save(incidenteToSave)).thenReturn(savedIncidente);

        Incidente result = incidenteRepositoryImpl.crear(incidenteToSave);

        assertNotNull(result);
        assertEquals(savedIncidente, result);
    }

    @Test
    void actualizarReturnsUpdatedIncidenteWhenExists() {
        Incidente incidenteToUpdate = new Incidente();
        incidenteToUpdate.setId(1);
        Incidente updatedIncidente = new Incidente();

        when(incidenteRepositoryJpa.existsById(incidenteToUpdate.getId())).thenReturn(true);
        when(incidenteRepositoryJpa.save(incidenteToUpdate)).thenReturn(updatedIncidente);

        Incidente result = incidenteRepositoryImpl.actualizar(incidenteToUpdate);

        assertNotNull(result);
        assertEquals(updatedIncidente, result);
    }

    @Test
    void actualizarReturnsNullWhenIncidenteDoesNotExist() {
        Incidente incidenteToUpdate = new Incidente();
        incidenteToUpdate.setId(1);

        when(incidenteRepositoryJpa.existsById(incidenteToUpdate.getId())).thenReturn(false);

        Incidente result = incidenteRepositoryImpl.actualizar(incidenteToUpdate);

        assertNull(result);
    }
}