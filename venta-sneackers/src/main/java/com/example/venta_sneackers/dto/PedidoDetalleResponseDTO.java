package com.example.venta_sneackers.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleResponseDTO {

    private Long detallePedidoId;
    private Long pedidoId;
    private String pedidoNombre;
    private Long clienteId;
    private String clienteNombre;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
