package com.example.venta_sneackers.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.venta_sneackers.Service.ClienteService;
import com.example.venta_sneackers.dto.ClienteRequestDTO;
import com.example.venta_sneackers.dto.ClienteResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteResponseDTO clienteResponse;
    private ClienteRequestDTO clienteRequest;

    @BeforeEach
    void setUp() {
        clienteResponse = new ClienteResponseDTO(1L, "Juan Perez", "Calle 123", "Habilitado");
        clienteRequest = new ClienteRequestDTO("Juan Perez", "Calle 123", "Habilitado");
    }

    @Test
    public void testObtenerTodos() throws Exception {
        when(clienteService.obtenerTodos()).thenReturn(List.of(clienteResponse));

        mockMvc.perform(get("/api/V1/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.buscarPorIdCliente.href").exists())
                .andExpect(jsonPath("$._links.buscarPorNombre.href").exists())
                .andExpect(jsonPath("$._links.buscarPorEstado.href").exists())
                .andExpect(jsonPath("$..id").value(hasItem(1)))
                .andExpect(jsonPath("$..cliNombre").value(hasItem("Juan Perez")));
    }

    @Test
    public void testObtenerTodosSinResultados() throws Exception {
        when(clienteService.obtenerTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/V1/clientes"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No encontrado"))
                .andExpect(jsonPath("$.descripcion").exists());
    }

    @Test
    public void testObtenerPorIdCliente() throws Exception {
        when(clienteService.obtenerPorIdCliente(1L)).thenReturn(List.of(clienteResponse));

        mockMvc.perform(get("/api/V1/clientes/idCliente/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$..id").value(hasItem(1)))
                .andExpect(jsonPath("$..cliNombre").value(hasItem("Juan Perez")));
    }

    @Test
    public void testObtenerPorNombre() throws Exception {
        when(clienteService.buscarPorNombre("Juan Perez")).thenReturn(List.of(clienteResponse));

        mockMvc.perform(get("/api/V1/clientes/cliNombre/Juan Perez"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$..cliNombre").value(hasItem("Juan Perez")));
    }

    @Test
    public void testCrearCliente() throws Exception {
        when(clienteService.guardar(any(ClienteRequestDTO.class))).thenReturn(clienteResponse);

        mockMvc.perform(post("/api/V1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliNombre").value("Juan Perez"));
    }

    @Test
    public void testActualizarCliente() throws Exception {
        when(clienteService.actualizar(eq(1L), any(ClienteRequestDTO.class))).thenReturn(clienteResponse);

        mockMvc.perform(put("/api/V1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliNombre").value("Juan Perez"));
    }

    @Test
    public void testEliminarCliente() throws Exception {
        doNothing().when(clienteService).eliminar(1L);

        mockMvc.perform(delete("/api/V1/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).eliminar(1L);
    }
}
