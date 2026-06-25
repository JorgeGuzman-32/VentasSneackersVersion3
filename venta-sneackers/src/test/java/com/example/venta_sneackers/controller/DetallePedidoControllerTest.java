package com.example.venta_sneackers.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MockMvc;

import com.example.venta_sneackers.Service.PedidoService;
import com.example.venta_sneackers.dto.PedidoDetalleResponseDTO;

@WebMvcTest(DetallePedidoController.class)
class DetallePedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    private PedidoDetalleResponseDTO detalle;

    @BeforeEach
    void setUp() {
        detalle = new PedidoDetalleResponseDTO(
                1L,
                100L,
                "PED-100",
                1L,
                "Juan Perez",
                50L,
                "Air Max",
                2,
                new BigDecimal("59990"),
                new BigDecimal("119980"));
    }

    @Test
    void testObtenerTodosLosDetallesPedido() throws Exception {
        when(pedidoService.obtenerTodosDetalles()).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/V1/detalle-pedidos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.buscarPorPedido.href").exists())
                .andExpect(jsonPath("$._links.buscarPorProducto.href").exists())
                .andExpect(jsonPath("$..detallePedidoId").value(hasItem(1)))
                .andExpect(jsonPath("$..productoNombre").value(hasItem("Air Max")));
    }

    @Test
    void testObtenerTodosLosDetallesPedidoSinResultados() throws Exception {
        when(pedidoService.obtenerTodosDetalles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/V1/detalle-pedidos"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No encontrado"));
    }

    @Test
    void testObtenerDetallePedidoPorIdPedido() throws Exception {
        when(pedidoService.obtenerDetallesPorPedidoId(100L)).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/V1/detalle-pedidos/pedido/100"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$..pedidoId").value(hasItem(100)));
    }

    @Test
    void testObtenerDetallePedidoPorIdProducto() throws Exception {
        when(pedidoService.obtenerDetallesPorProductoId(50L)).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/V1/detalle-pedidos/producto/50"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$..productoId").value(hasItem(50)));
    }
}
