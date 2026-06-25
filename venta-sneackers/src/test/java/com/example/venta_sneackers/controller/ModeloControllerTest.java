package com.example.venta_sneackers.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.venta_sneackers.Service.ModeloService;
import com.example.venta_sneackers.dto.ModeloRequestDTO;
import com.example.venta_sneackers.dto.ModeloResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ModeloController.class)
class ModeloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModeloService modeloService;

    @Autowired
    private ObjectMapper objectMapper;

    private ModeloResponseDTO modeloResponse;
    private ModeloRequestDTO modeloRequest;

    @BeforeEach
    void setUp() {
        modeloResponse = new ModeloResponseDTO(1L, "Modelo premium", "Air Max", "Verano", 2026, false);
        modeloRequest = new ModeloRequestDTO("Air Max", "Verano", 2026, false, "Modelo premium");
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(modeloService.obtenerTodos()).thenReturn(List.of(modeloResponse));

        mockMvc.perform(get("/api/V1/modelos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.buscarPorId.href").exists())
                .andExpect(jsonPath("$._links.buscarPorNombre.href").exists())
                .andExpect(jsonPath("$..id").value(hasItem(1)))
                .andExpect(jsonPath("$..modNombre").value(hasItem("Air Max")));
    }

    @Test
    void testObtenerTodosSinResultados() throws Exception {
        when(modeloService.obtenerTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/V1/modelos"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No encontrado"));
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(modeloService.obtenerPorId(1L)).thenReturn(Optional.of(modeloResponse));

        mockMvc.perform(get("/api/V1/modelos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCrearModelo() throws Exception {
        when(modeloService.guardar(any(ModeloRequestDTO.class))).thenReturn(modeloResponse);

        mockMvc.perform(post("/api/V1/modelos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modeloRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modNombre").value("Air Max"));
    }

    @Test
    void testActualizarModelo() throws Exception {
        when(modeloService.actualizar(eq(1L), any(ModeloRequestDTO.class))).thenReturn(Optional.of(modeloResponse));

        mockMvc.perform(put("/api/V1/modelos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modeloRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testEliminarModelo() throws Exception {
        doNothing().when(modeloService).eliminar(1L);

        mockMvc.perform(delete("/api/V1/modelos/1"))
                .andExpect(status().isNoContent());
    }
}
