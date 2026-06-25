package com.example.venta_sneackers.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.venta_sneackers.Service.ProductoService;
import com.example.venta_sneackers.dto.ProductoRequestDTO;
import com.example.venta_sneackers.dto.ProductoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoResponseDTO productoResponse;
    private ProductoRequestDTO productoRequest;

    @BeforeEach
    void setUp() {
        productoResponse = new ProductoResponseDTO(
                1L,
                "Air Max",
                "Zapatilla running",
                "2026-06-01",
                new BigDecimal("119990"),
                20,
                "Nike",
                "Negro",
                "Unisex",
                "Adulto",
                42L,
                10L);

        productoRequest = new ProductoRequestDTO(
                "Air Max",
                "Zapatilla running",
                "2026-06-01",
                new BigDecimal("119990"),
                20,
                "Nike",
                "Negro",
                "Unisex",
                "Adulto",
                42L,
                10L);
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(productoService.obtenerTodos()).thenReturn(List.of(productoResponse));

        mockMvc.perform(get("/api/V1/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.buscarPorId.href").exists())
                .andExpect(jsonPath("$._links.buscarPorNombre.href").exists())
                .andExpect(jsonPath("$..id").value(hasItem(1)))
                .andExpect(jsonPath("$..proNombre").value(hasItem("Air Max")));
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(productoService.obtenerProdPorId(1L)).thenReturn(Optional.of(productoResponse));

        mockMvc.perform(get("/api/V1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.proNombre").value("Air Max"));
    }

    @Test
    void testCrearProducto() throws Exception {
        when(productoService.crearProducto(any(ProductoRequestDTO.class))).thenReturn(productoResponse);

        mockMvc.perform(post("/api/V1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.proNombre").value("Air Max"));
    }

    @Test
    void testEliminarProducto() throws Exception {
        doReturn(true).when(productoService).eliminarProducto(1L);

        mockMvc.perform(delete("/api/V1/productos/1"))
                .andExpect(status().isOk());
    }
}
