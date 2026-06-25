package com.example.venta_sneackers.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.venta_sneackers.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedido_IdPedido(Long pedidoId);


    List<DetallePedido> findByProducto_IdProducto(Long productoId);

    List<DetallePedido> findByPedido_Cliente_IdCliente(Long clienteId);

    List<DetallePedido> findByCantidadGreaterThan(Integer cantidad);

    List<DetallePedido> findByCantidadLessThan(Integer cantidad);

    List<DetallePedido> findByPrecioUnitarioGreaterThan(BigDecimal precioUnitario);

    List<DetallePedido> findByPrecioUnitarioLessThan(BigDecimal precioUnitario);

}
