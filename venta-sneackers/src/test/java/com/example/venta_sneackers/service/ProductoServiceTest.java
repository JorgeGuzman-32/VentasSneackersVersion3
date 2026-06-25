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

import com.example.venta_sneackers.Service.ProductoService;
import com.example.venta_sneackers.dto.ProductoRequestDTO;
import com.example.venta_sneackers.dto.ProductoResponseDTO;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.ModeloRepository;
import com.example.venta_sneackers.repository.ProductoRepository;
import com.example.venta_sneackers.repository.TallaRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private TallaRepository tallaRepository;

    @Mock
    private ModeloRepository modeloRepository;

    @Test
    void testObtenerTodos() {
        Talla talla = new Talla(42L, "42", "EU");
        Modelo modelo = new Modelo(10L, "Running", "Air Max", "Verano", 2026, false);
        Producto producto = new Producto(1L, "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("100"), 10,
                "Nike", "Negro", "Unisex", "Adulto", talla, modelo);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoResponseDTO> productos = productoService.obtenerTodos();

        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Air Max", productos.get(0).getProNombre());
    }

    @Test
    void testCrearProducto() {
        ProductoRequestDTO request = new ProductoRequestDTO(
                "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("100"), 10,
                "Nike", "Negro", "Unisex", "Adulto", 42L, 10L);

        Talla talla = new Talla(42L, "42", "EU");
        Modelo modelo = new Modelo(10L, "Running", "Air Max", "Verano", 2026, false);
        Producto guardado = new Producto(1L, "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("100"), 10,
                "Nike", "Negro", "Unisex", "Adulto", talla, modelo);

        when(tallaRepository.findById(42L)).thenReturn(Optional.of(talla));
        when(modeloRepository.findById(10L)).thenReturn(Optional.of(modelo));
        when(productoRepository.save(any(Producto.class))).thenReturn(guardado);

        ProductoResponseDTO response = productoService.crearProducto(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(42L, response.getTallaId());
        assertEquals(10L, response.getModeloId());
    }

    @Test
    void testActualizarStock() {
        Talla talla = new Talla(42L, "42", "EU");
        Modelo modelo = new Modelo(10L, "Running", "Air Max", "Verano", 2026, false);
        Producto producto = new Producto(1L, "Air Max", "Zapatilla", "2026-06-25", new BigDecimal("100"), 10,
                "Nike", "Negro", "Unisex", "Adulto", talla, modelo);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        boolean actualizado = productoService.actualizarStock(1L, 20);

        assertTrue(actualizado);
        assertEquals(20, producto.getStock());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testEliminarProducto() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = productoService.eliminarProducto(1L);

        assertTrue(eliminado);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
