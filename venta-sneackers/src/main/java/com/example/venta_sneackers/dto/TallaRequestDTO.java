package com.example.venta_sneackers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TallaRequestDTO {

    @NotBlank(message = "El nombre de la talla es obligatorio")
    private String tallNombre;

    @NotBlank(message = "La unidad es obligatoria")
    private String unidad;

    

    public TallaRequestDTO orElse(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }
}
