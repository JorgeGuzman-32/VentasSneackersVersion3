package com.example.venta_sneackers.repository;

import com.example.venta_sneackers.model.Pedido;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Repositorio JPA para operaciones CRUD y queries personalizadas de Pedido
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // ==================== QUERY METHODS ====================
    
    // Buscar pedidos por cliente
    List<Pedido> findByCliente_IdCliente(Long clienteId);

    // Buscar pedidos por fecha de compra
    List<Pedido> findByPedFechaCompra(String pedFechaCompra);

    // Buscar pedidos por rango de total
    List<Pedido> findByPedTotalGreaterThan(BigDecimal pedTotal);

    List<Pedido> findByPedTotalLessThan(BigDecimal pedTotal);

    List<Pedido> findByPedTotalBetween(BigDecimal totalMin, BigDecimal totalMax);

    // Buscar pedidos por rango de descuento
    List<Pedido> findByPedDescuentoBetween(BigDecimal descuentoMin, BigDecimal descuentoMax);

    // Buscar pedidos por rango de subtotal
    List<Pedido> findByPedSubtotalBetween(BigDecimal subtotalMin, BigDecimal subtotalMax);

    // Buscar pedidos por estado de pago
    List<Pedido> findByPedPagado(boolean pedPagado);

    // Buscar pedidos por producto (a través de detalles)
    List<Pedido> findByDetalles_Producto_IdProducto(Long productoId);

    // Buscar pedidos por nombre de producto (a través de detalles)
    List<Pedido> findByDetalles_Producto_ProNombreContainingIgnoreCase(String proNombre);

    // Buscar pedidos por nombre del cliente
    List<Pedido> findByCliente_CliNombreContainingIgnoreCase(String cliNombre);

    // Buscar pedidos por estado del cliente
    List<Pedido> findByCliente_CliEstadoContainingIgnoreCase(String cliEstado);

    // Buscar pedidos por dirección del cliente
    List<Pedido> findByCliente_CliDireccionContainingIgnoreCase(String cliDireccion);

    // Obtener pedido por total exacto
    List<Pedido> findByPedTotal(BigDecimal pedTotal);

    // ==================== CUSTOM JPQL QUERIES ====================

    // Buscar pedidos por total mayor al especificado
    @Query("SELECT p FROM Pedido p WHERE p.pedTotal > :total")
    List<Pedido> findPedidosByTotalGreaterThan(@Param("total") BigDecimal total);

    // Buscar pedidos por fecha de compra
    @Query("SELECT p FROM Pedido p WHERE p.pedFechaCompra = :fecha")
    List<Pedido> findPedidosByFechaCompra(@Param("fecha") String fecha);

    // Buscar pedidos con descuento mayor a cero
    @Query("SELECT p FROM Pedido p WHERE p.pedDescuento > 0")
    List<Pedido> findPedidosConDescuento();

    // Buscar pedidos pagados
    @Query("SELECT p FROM Pedido p WHERE p.pedPagado = true")
    List<Pedido> findPedidosPagados();

    // Buscar pedidos sin pagar
    @Query("SELECT p FROM Pedido p WHERE p.pedPagado = false")
    List<Pedido> findPedidosSinPagar();
}
