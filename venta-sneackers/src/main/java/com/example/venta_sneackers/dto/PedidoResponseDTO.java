package com.example.venta_sneackers.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private Long idPedido;
    private String pedNumero;
    private String pedFechaCompra;
    private BigDecimal pedSubtotal;
    private BigDecimal pedDescuento;
    private BigDecimal pedTotal;
    private Boolean pedPagado;
    private Long clienteId;
}
