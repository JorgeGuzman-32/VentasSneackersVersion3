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

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.venta_sneackers.Service.TallaService;
import com.example.venta_sneackers.dto.TallaRequestDTO;
import com.example.venta_sneackers.dto.TallaResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TallaController.class)
class TallaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TallaService tallaService;

    @Autowired
    private ObjectMapper objectMapper;

    private TallaResponseDTO tallaResponse;
    private TallaRequestDTO tallaRequest;

    @BeforeEach
    void setUp() {
        tallaResponse = new TallaResponseDTO(1L, "42", "EU");
        tallaRequest = new TallaRequestDTO("42", "EU");
    }

    @Test
    void testObtenerTodas() throws Exception {
        when(tallaService.obtenerTodas()).thenReturn(List.of(tallaResponse));

        mockMvc.perform(get("/api/V1/tallas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.buscarPorId.href").exists())
                .andExpect(jsonPath("$..id").value(hasItem(1)))
                .andExpect(jsonPath("$..tallNombre").value(hasItem("42")));
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(tallaService.obtenerPorId(1L)).thenReturn(Optional.of(tallaResponse));

        mockMvc.perform(get("/api/V1/tallas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGuardarTalla() throws Exception {
        when(tallaService.guardar(any(TallaRequestDTO.class))).thenReturn(tallaResponse);

        mockMvc.perform(post("/api/V1/tallas/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tallaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizarTalla() throws Exception {
        when(tallaService.actualizar(eq(1L), any(TallaRequestDTO.class))).thenReturn(tallaResponse);

        mockMvc.perform(put("/api/V1/tallas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tallaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testEliminarTalla() throws Exception {
        doNothing().when(tallaService).eliminar(1L);

        mockMvc.perform(delete("/api/V1/tallas/1"))
                .andExpect(status().isNoContent());
    }
}
