package com.example.venta_sneackers.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Zapatos deportivos")
    private String proNombre;

    @Schema(description = "Descripción del producto", example = "Zapatos para correr")
    private String proDescripcion;

    @Schema(description = "Fecha de ingreso del producto", example = "2024-06-15")
    private String proFechaIngreso;

    @Schema(description = "Precio del producto", example = "100.00")
    private BigDecimal proPrecio;

    @Schema(description = "Stock del producto", example = "10")
    private Integer stock;

    @Schema(description = "Marca del producto", example = "Nike")
    private String proMarca;

    @Schema(description = "Color del producto", example = "Negro")
    private String proColor;

    @Schema(description = "Género del producto", example = "Masculino")
    private String proGenero;

    @Schema(description = "Edad del producto", example = "Adulto")
    private String proEdad;

    @Schema(description = "ID de la talla del producto", example = "1")
    private Long tallaId;
    
    @Schema(description = "ID del modelo del producto", example = "1")
    private Long modeloId;
}

