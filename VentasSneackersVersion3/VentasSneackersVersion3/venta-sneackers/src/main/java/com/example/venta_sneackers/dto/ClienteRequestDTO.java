package com.example.venta_sneackers.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    //NO TIENE CAMPO "ID"

    @NotBlank(message = "El nombre es obligatorio")
    private String cliNombre;

    @NotBlank(message = "La direccion es obligatoria")
    private String cliDireccion;

    @NotBlank(message = "El estado es obligatorio")
    private String cliEstado;



}
