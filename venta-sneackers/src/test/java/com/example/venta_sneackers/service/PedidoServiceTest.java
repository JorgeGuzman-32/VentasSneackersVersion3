package com.example.venta_sneackers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.venta_sneackers.Service.PedidoService;
import com.example.venta_sneackers.dto.PedidoDetalleRequestDTO;
import com.example.venta_sneackers.dto.PedidoRequestDTO;
import com.example.venta_sneackers.dto.PedidoResponseDTO;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.model.DetallePedido;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.ClienteRepository;
import com.example.venta_sneackers.repository.PedidoRepository;
import com.example.venta_sneackers.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Test
    void testGuardar() {
        Cliente cliente = new Cliente(1L, "Juan", "Calle 123", "Habilitado");
        Talla talla = new Talla(42L, "42", "EU");
        Modelo modelo = new Modelo(10L, "Running", "Air Max", "Verano", 2026, false);
        Producto producto = new Producto(2L, "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("50000"), 12,
                "Nike", "Negro", "Unisex", "Adulto", talla, modelo);

        PedidoDetalleRequestDTO item = new PedidoDetalleRequestDTO(2L, 2, new BigDecimal("50000"));
        PedidoRequestDTO request = new PedidoRequestDTO(
                "2026-06-25",
                new BigDecimal("100000"),
                BigDecimal.ZERO,
                new BigDecimal("100000"),
                1L,
                true,
                List.of(item));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(2L)).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setIdPedido(1L);
            if (!pedido.getDetalles().isEmpty()) {
                pedido.getDetalles().get(0).setIdDetallePedido(100L);
            }
            return pedido;
        });

        PedidoResponseDTO response = pedidoService.guardar(request);

        assertNotNull(response);
        assertEquals(1L, response.getIdPedido());
        assertEquals(new BigDecimal("100000"), response.getPedTotal());
        assertEquals(1, response.getItems().size());
    }

    @Test
    void testGuardarSinItemsLanzaExcepcion() {
        PedidoRequestDTO request = new PedidoRequestDTO(
                "2026-06-25",
                new BigDecimal("100000"),
                BigDecimal.ZERO,
                new BigDecimal("100000"),
                1L,
                true,
                List.of());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> pedidoService.guardar(request));
        assertEquals("El pedido debe contener al menos un item", ex.getMessage());
    }

    @Test
    void testObtenerPorId() {
        Cliente cliente = new Cliente(1L, "Juan", "Calle 123", "Habilitado");
        Talla talla = new Talla(42L, "42", "EU");
        Modelo modelo = new Modelo(10L, "Running", "Air Max", "Verano", 2026, false);
        Producto producto = new Producto(2L, "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("50000"), 12,
                "Nike", "Negro", "Unisex", "Adulto", talla, modelo);

        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setPedFechaCompra("2026-06-25");
        pedido.setPedSubtotal(new BigDecimal("100000"));
        pedido.setPedDescuento(BigDecimal.ZERO);
        pedido.setPedTotal(new BigDecimal("100000"));
        pedido.setPedPagado(true);
        pedido.setCliente(cliente);
        pedido.setDetalles(new ArrayList<>());

        DetallePedido detalle = new DetallePedido(100L, pedido, producto, 2, new BigDecimal("50000"), new BigDecimal("100000"));
        pedido.getDetalles().add(detalle);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO response = pedidoService.obtenerPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getIdPedido());
        assertEquals("PED-1", response.getPedNumero());
        assertEquals(1, response.getItems().size());
    }

    @Test
    void testActualizarEstadoPago() {
        Cliente cliente = new Cliente(1L, "Juan", "Calle 123", "Habilitado");
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setPedPagado(false);
        pedido.setCliente(cliente);
        pedido.setDetalles(new ArrayList<>());

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        pedidoService.actualizarEstadoPago(1L, true);

        assertTrue(pedido.getPedPagado());
        verify(pedidoRepository, times(1)).save(pedido);
    }
}
