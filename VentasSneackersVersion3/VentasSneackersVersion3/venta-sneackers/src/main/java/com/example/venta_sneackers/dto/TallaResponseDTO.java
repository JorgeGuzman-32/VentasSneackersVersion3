package com.example.venta_sneackers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TallaResponseDTO {

    private Long id;
    private String tallNombre;
    private String unidad;
}
