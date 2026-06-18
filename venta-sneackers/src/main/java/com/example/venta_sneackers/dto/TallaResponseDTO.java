package com.example.venta_sneackers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TallaResponseDTO {

    @Schema(description = "ID de la talla", example = "1")
    private Long id;

    @Schema(description = "Nombre de la talla", example = "Talla 42")
    private String tallNombre;

    @Schema(description = "Unidad de medida de la talla", example = "EU")
    private String unidad;
}
