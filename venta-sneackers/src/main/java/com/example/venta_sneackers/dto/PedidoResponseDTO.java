package com.example.venta_sneackers.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    @Schema(description = "ID del pedido", example = "1")
    private Long idPedido;


    @Schema(description = "Número del pedido", example = "PED-001")
    private String pedNumero;

    @Schema(description = "Fecha de compra del pedido", example = "2024-06-15")
    private String pedFechaCompra;

    @Schema(description = "Subtotal del pedido", example = "100.00")
    private BigDecimal pedSubtotal;

    @Schema(description = "Descuento aplicado al pedido", example = "10.00")
    private BigDecimal pedDescuento;

    @Schema(description = "Total del pedido después de aplicar el descuento", example = "90.00")
    private BigDecimal pedTotal;

    @Schema(description = "ID del cliente que realizó el pedido", example = "1")
    private Long clienteId;

    private java.util.List<PedidoDetalleResponseDTO> items;
}
