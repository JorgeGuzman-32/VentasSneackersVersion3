package com.example.venta_sneackers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeloRequestDTO {

    @NotBlank(message = "El nombre del modelo es obligatorio")
    private String modNombre;

    @NotBlank(message = "La temporada del modelo es obligatoria")
    private String modTemporada;

    @NotBlank(message = "El año de lanzamiento del modelo es obligatorio")
    private Integer modAnioLanzamiento;

    @NotBlank(message = "Indicar si el modelo es de edición limitada es obligatorio")
    private Boolean modEdicionLimitada;

    @NotBlank(message = "La descripción del modelo es obligatoria")
    private String modDescripcion;


}
