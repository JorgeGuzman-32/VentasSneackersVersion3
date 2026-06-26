package com.example.venta_sneackers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.example.venta_sneackers.Service.ClienteService;
import com.example.venta_sneackers.dto.ClienteRequestDTO;
import com.example.venta_sneackers.dto.ClienteResponseDTO;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void testObtenerTodos() {
        when(clienteRepository.findAll()).thenReturn(List.of(
                new Cliente(1L, "Juan Perez", "Calle 123", "Habilitado")
        ));

        List<ClienteResponseDTO> clientes = clienteService.obtenerTodos();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Juan Perez", clientes.get(0).getCliNombre());
    }

    @Test
    void testObtenerPorId() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(
                new Cliente(1L, "Juan Perez", "Calle 123", "Habilitado")
        ));

        Optional<ClienteResponseDTO> cliente = clienteService.obtenerPorId(1L);

        assertEquals(true, cliente.isPresent());
        assertEquals("Juan Perez", cliente.get().getCliNombre());
    }

    @Test
    void testGuardar() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(
                new Cliente(1L, "Juan Perez", "Calle 123", "Habilitado")
        );

        ClienteResponseDTO creado = clienteService.guardar(
                new ClienteRequestDTO("Juan Perez", "Calle 123", "Habilitado")
        );

        assertNotNull(creado);
        assertEquals(1L, creado.getId());
        assertEquals("Habilitado", creado.getCliEstado());
    }

    @Test
    void testEliminar() {
        clienteService.eliminar(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
