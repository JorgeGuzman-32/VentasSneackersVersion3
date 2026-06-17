package com.example.venta_sneackers.repository;

import com.example.venta_sneackers.model.Pedido;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // TIPO 1 : QUERY METHODS

    List<Pedido> findByCliente_IdCliente(Long clienteId);

    List<Pedido> findByPedFechaCompra(String pedFechaCompra);

    List<Pedido> findByPedTotalGreaterThan(BigDecimal pedTotal);

    List<Pedido> findByPedTotalLessThan(BigDecimal pedTotal);

    List<Pedido> findByPedDescuentoBetween(BigDecimal descuentoMin, BigDecimal descuentoMax);

    List<Pedido> findByPedSubtotalBetween(BigDecimal subtotalMin, BigDecimal subtotalMax);

    List<Pedido> findByPedTotalBetween(BigDecimal totalMin, BigDecimal totalMax);

    List<Pedido> findByPedPagado(boolean pedPagado);

    // OBTENER PEDIDO POR EL TOTAL
    List<Pedido> findByPedTotal(BigDecimal pedTotal);

    



    //Metodo 2 : @Query CON JPQL

    @Query("SELECT p FROM Pedido p WHERE p.pedTotal > :total")
    List<Pedido> findPedidosByTotalGreaterThan(@Param("total") BigDecimal total);

    @Query("SELECT p FROM Pedido p WHERE p.pedFechaCompra = :fecha")
    List<Pedido> findPedidosByFechaCompra(@Param("fecha") String fecha);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.idCliente = :clienteId")
    List<Pedido> findPedidosByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT p FROM Pedido p WHERE p.pedDescuento BETWEEN :descuentoMin AND :descuentoMax")
    List<Pedido> findPedidosByDescuentoBetween(@Param("descuentoMin") BigDecimal descuentoMin, @Param("descuentoMax") BigDecimal descuentoMax);

    @Query("SELECT p FROM Pedido p WHERE p.pedSubtotal BETWEEN :subtotalMin AND :subtotalMax")
    List<Pedido> findPedidosBySubtotalBetween(@Param("subtotalMin") BigDecimal subtotalMin, @Param("subtotalMax") BigDecimal subtotalMax);

    @Query("SELECT p FROM Pedido p WHERE p.pedTotal BETWEEN :totalMin AND :totalMax")
    List<Pedido> findPedidosByTotalBetween(@Param("totalMin") BigDecimal totalMin, @Param("totalMax") BigDecimal totalMax);

    @Query("SELECT p FROM Pedido p WHERE p.pedPagado = :pedPagado")
    List<Pedido> findPedidosByPagado(@Param("pedPagado") boolean pedPagado);

}
