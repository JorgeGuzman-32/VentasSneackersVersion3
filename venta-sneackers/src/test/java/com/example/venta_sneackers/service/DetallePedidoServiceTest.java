package com.example.venta_sneackers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.venta_sneackers.Service.DetallePedidoService;
import com.example.venta_sneackers.dto.PedidoDetalleResponseDTO;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.model.DetallePedido;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.DetallePedidoRepository;

@ExtendWith(MockitoExtension.class)
public class DetallePedidoServiceTest {

    @InjectMocks
    private DetallePedidoService detallePedidoService;

    @Mock
    private DetallePedidoRepository detallePedidoRepository;

    @Test
    void testObtenerTodos() {
        Cliente cliente = new Cliente(1L, "Juan", "Calle 123", "Habilitado");
        Pedido pedido = new Pedido();
        pedido.setIdPedido(100L);
        pedido.setCliente(cliente);

        Talla talla = new Talla(42L, "42", "EU");
        Modelo modelo = new Modelo(10L, "Running", "Air Max", "Verano", 2026, false);
        Producto producto = new Producto(2L, "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("50000"), 12,
                "Nike", "Negro", "Unisex", "Adulto", talla, modelo);

        DetallePedido detalle = new DetallePedido(1L, pedido, producto, 2, new BigDecimal("50000"), new BigDecimal("100000"));

        when(detallePedidoRepository.findAll()).thenReturn(List.of(detalle));

        List<PedidoDetalleResponseDTO> respuesta = detallePedidoService.obtenerTodos();

        assertNotNull(respuesta);
        assertEquals(1, respuesta.size());
        assertEquals("PED-100", respuesta.get(0).getPedidoNombre());
        assertEquals("Juan", respuesta.get(0).getClienteNombre());
    }

    @Test
    void testObtenerPorId() {
        DetallePedido detalle = new DetallePedido();
        detalle.setIdDetallePedido(1L);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("50000"));
        detalle.setSubtotal(new BigDecimal("100000"));

        when(detallePedidoRepository.findById(1L)).thenReturn(Optional.of(detalle));

        Optional<PedidoDetalleResponseDTO> response = detallePedidoService.obtenerPorId(1L);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getDetallePedidoId());
        assertEquals(2, response.get().getCantidad());
    }

    @Test
    void testGuardar() {
        PedidoDetalleResponseDTO dto = new PedidoDetalleResponseDTO(
                null, null, null, null, null, null, null, 2,
                new BigDecimal("50000"), new BigDecimal("100000"));

        DetallePedido saved = new DetallePedido();
        saved.setIdDetallePedido(1L);
        saved.setCantidad(2);
        saved.setPrecioUnitario(new BigDecimal("50000"));
        saved.setSubtotal(new BigDecimal("100000"));

        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(saved);

        PedidoDetalleResponseDTO response = detallePedidoService.guardar(dto);

        assertNotNull(response);
        assertEquals(1L, response.getDetallePedidoId());
        assertEquals(new BigDecimal("100000"), response.getSubtotal());
    }

    @Test
    void testEliminar() {
        detallePedidoService.eliminar(1L);
        verify(detallePedidoRepository, times(1)).deleteById(1L);
    }
}
