package com.example.venta_sneackers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta con los datos de un modelo")
public class ModeloResponseDTO {

    @Schema(description = "Identificador único del modelo", example = "1")
    private Long id;

    @Schema(description = "Descripción del modelo", example = "Zapatilla deportiva de alto rendimiento")
    private String modDescripcion;

    @Schema(description = "Nombre del modelo", example = "Air Max")
    private String modNombre;

    @Schema(description = "Temporada del modelo", example = "Verano")
    private String modTemporada;

    @Schema(description = "Año de lanzamiento del modelo", example = "2024")
    private Integer modAnioLanzamiento;

    @Schema(description = "Indica si el modelo es de edición limitada", example = "false")
    private Boolean modEdicionLimitada;

}
