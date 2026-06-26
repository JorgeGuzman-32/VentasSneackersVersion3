package com.example.venta_sneackers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.venta_sneackers.Service.TallaService;
import com.example.venta_sneackers.dto.TallaRequestDTO;
import com.example.venta_sneackers.dto.TallaResponseDTO;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.TallaRepository;

@ExtendWith(MockitoExtension.class)
public class TallaServiceTest {

    @InjectMocks
    private TallaService tallaService;

    @Mock
    private TallaRepository tallaRepository;

    @Test
    void testObtenerTodas() {
        when(tallaRepository.findAll()).thenReturn(List.of(
                new Talla(1L, "42", "EU")
        ));

        List<TallaResponseDTO> tallas = tallaService.obtenerTodas();

        assertNotNull(tallas);
        assertEquals(1, tallas.size());
        assertEquals("42", tallas.get(0).getTallNombre());
    }

    @Test
    void testObtenerPorId() {
        when(tallaRepository.findById(1L)).thenReturn(Optional.of(new Talla(1L, "42", "EU")));

        Optional<TallaResponseDTO> talla = tallaService.obtenerPorId(1L);

        assertTrue(talla.isPresent());
        assertEquals("EU", talla.get().getUnidad());
    }

    @Test
    void testGuardar() {
        when(tallaRepository.save(any(Talla.class))).thenReturn(new Talla(1L, "42", "EU"));

        TallaResponseDTO guardada = tallaService.guardar(new TallaRequestDTO("42", "EU"));

        assertNotNull(guardada);
        assertEquals(1L, guardada.getId());
    }

    @Test
    void testEliminar() {
        tallaService.eliminar(1L);
        verify(tallaRepository, times(1)).deleteById(1L);
    }
}
