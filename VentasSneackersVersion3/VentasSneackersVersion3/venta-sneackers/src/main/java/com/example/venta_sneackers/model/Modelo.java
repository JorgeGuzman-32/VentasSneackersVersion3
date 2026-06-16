package com.example.venta_sneackers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModelo;

    @Column(nullable = false, length = 100)
    private String modDescripcion;

    @Column(nullable = false, length = 100)
    private String modNombre;

    @Column(nullable = false, length = 50)
    private String modTemporada;

    @Column(nullable = false)
    private Integer modAnioLanzamiento;

    @Column(nullable = false)
    private Boolean modEdicionLimitada;
}
