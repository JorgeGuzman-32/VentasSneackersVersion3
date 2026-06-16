package com.example.venta_sneackers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String proNombre;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    private String proDescripcion;

    @NotBlank(message = "La fecha de ingreso es obligatoria")
    private String proFechaIngreso;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal proPrecio;

    @NotNull(message = "El stock es obligatorio")
    @Positive(message = "El stock debe ser mayor a 0")
    private Integer stock;

    @NotBlank(message = "La marca es obligatoria")
    private String proMarca;

    @NotBlank(message = "El color es obligatorio")
    private String proColor;

    @NotBlank(message = "El género es obligatorio")
    private String proGenero;

    @NotBlank(message = "La edad es obligatoria")
    private String proEdad;

    @NotNull(message = "La talla es obligatoria")
    private Long tallaId;

    @NotNull(message = "El modelo es obligatorio")
    private Long modeloId;
}
