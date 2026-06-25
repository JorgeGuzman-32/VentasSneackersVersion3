package com.example.venta_sneackers.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.venta_sneackers.Service.PedidoService;
import com.example.venta_sneackers.dto.PedidoDetalleRequestDTO;
import com.example.venta_sneackers.dto.PedidoRequestDTO;
import com.example.venta_sneackers.dto.PedidoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PedidoRequestDTO pedidoRequest;
    private PedidoResponseDTO pedidoResponse;

    @BeforeEach
    void setUp() {
        List<PedidoDetalleRequestDTO> items = List.of(new PedidoDetalleRequestDTO(2L, 1, new BigDecimal("59990")));
        pedidoRequest = new PedidoRequestDTO(
                "2026-06-25",
                new BigDecimal("59990"),
                new BigDecimal("0"),
                new BigDecimal("59990"),
                1L,
                true,
                items);

        pedidoResponse = new PedidoResponseDTO(
                1L,
                "PED-001",
                "2026-06-25",
                new BigDecimal("59990"),
                new BigDecimal("0"),
                new BigDecimal("59990"),
                1L,
                Collections.emptyList());
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(pedidoService.obtenerTodos()).thenReturn(List.of(pedidoResponse));

        mockMvc.perform(get("/api/V1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.buscarPorId.href").exists())
                .andExpect(jsonPath("$._links.buscarPorTotal.href").exists())
                .andExpect(jsonPath("$..idPedido").value(hasItem(1)))
                .andExpect(jsonPath("$..pedNumero").value(hasItem("PED-001")));
    }

    @Test
    void testObtenerTodosSinResultados() throws Exception {
        when(pedidoService.obtenerTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/V1/pedidos"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No hay pedidos"));
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(pedidoService.obtenerPorId(1L)).thenReturn(pedidoResponse);

        mockMvc.perform(get("/api/V1/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void testObtenerPorIdNoEncontrado() throws Exception {
        when(pedidoService.obtenerPorId(99L)).thenThrow(new IllegalArgumentException("Pedido no encontrado"));

        mockMvc.perform(get("/api/V1/pedidos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Pedido no encontrado"));
    }

    @Test
    void testGuardarPedido() throws Exception {
        when(pedidoService.guardar(any(PedidoRequestDTO.class))).thenReturn(pedidoResponse);

        mockMvc.perform(post("/api/V1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void testActualizarPedido() throws Exception {
        when(pedidoService.actualizar(eq(1L), any(PedidoRequestDTO.class))).thenReturn(pedidoResponse);

        mockMvc.perform(put("/api/V1/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void testEliminarPedido() throws Exception {
        doNothing().when(pedidoService).eliminar(1L);

        mockMvc.perform(delete("/api/V1/pedidos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarPedidoNoEncontrado() throws Exception {
        doThrow(new IllegalArgumentException("Pedido no encontrado")).when(pedidoService).eliminar(99L);

        mockMvc.perform(delete("/api/V1/pedidos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Pedido no encontrado"));
    }
}
