package com.example.venta_sneackers.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String proNombre;
    private String proDescripcion;
    private String proFechaIngreso;
    private BigDecimal proPrecio;
    private Integer stock;
    private String proMarca;
    private String proColor;
    private String proGenero;
    private String proEdad;
    private Long tallaId;
    private Long modeloId;
}

