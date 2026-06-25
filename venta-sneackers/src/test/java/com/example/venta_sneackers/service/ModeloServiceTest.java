package com.example.venta_sneackers.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.venta_sneackers.Service.ModeloService;
import com.example.venta_sneackers.dto.ModeloRequestDTO;
import com.example.venta_sneackers.dto.ModeloResponseDTO;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.repository.ModeloRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ModeloServiceTest {

    @Autowired
    private ModeloService modeloService;

    @MockitoBean
    private ModeloRepository modeloRepository;

    @Test
    public void testObtenerTodos() {
        when(modeloRepository.findAll()).thenReturn(List.of(
            new Modelo(1L, "Zapatilla clasica", "Air Max", "verano", 2020, false)
        ));

        List<ModeloResponseDTO> modelos = modeloService.obtenerTodos();

        assertNotNull(modelos);
        assertEquals(1, modelos.size());
        assertEquals("Air Max", modelos.get(0).getModNombre());
    }

    @Test
    public void testObtenerPorId() {
        Modelo modelo = new Modelo(1L, "Zapatilla clasica", "Air Max", "verano", 2020, false);
        when(modeloRepository.findById(1L)).thenReturn(Optional.of(modelo));

        Optional<ModeloResponseDTO> resultado = modeloService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Air Max", resultado.get().getModNombre());
    }

    @Test
    public void testGuardar() {
        Modelo modelo = new Modelo(1L, "Zapatilla clasica", "Air Max", "verano", 2020, false);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);

        ModeloRequestDTO dto = new ModeloRequestDTO("Air Max", "verano", 2020, false, "Zapatilla clasica");
        ModeloResponseDTO resultado = modeloService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Air Max", resultado.getModNombre());
    }

    @Test
    public void testEliminar() {
        doNothing().when(modeloRepository).deleteById(1L);

        modeloService.eliminar(1L);

        verify(modeloRepository, times(1)).deleteById(1L);
    }
}
