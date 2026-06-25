package com.example.venta_sneackers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "La fecha de compra es obligatoria")
    private String pedFechaCompra;

    @NotNull(message = "El subtotal es obligatorio")
    @Positive(message = "El subtotal debe ser mayor a 0")
    private BigDecimal pedSubtotal;

    @NotNull(message = "El descuento es obligatorio")
    @PositiveOrZero(message = "El descuento no puede ser negativo")
    private BigDecimal pedDescuento;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a 0")
    private BigDecimal pedTotal;

    @NotNull(message = "El id del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El estado de pago es obligatorio")
    private Boolean pedPagado;

    @Valid
    @NotEmpty(message = "El pedido debe contener al menos un item")
    private List<PedidoDetalleRequestDTO> items;
}






