package com.example.venta_sneackers.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeloResponseDTO {

    private Long id;
    private String modDescripcion;
    private String modNombre;
    private String modTemporada;
    private Integer modAnioLanzamiento;
    private Boolean modEdicionLimitada;


}
