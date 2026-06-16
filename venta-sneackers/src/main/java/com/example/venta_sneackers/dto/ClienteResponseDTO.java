package com.example.venta_sneackers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String cliNombre;
    private String cliDireccion;
    private String cliEstado;



}
