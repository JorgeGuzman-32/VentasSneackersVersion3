package com.example.venta_sneackers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false, length = 200)
    private String proNombre;

    @Column(nullable = false, length = 200)
    private String proDescripcion;

    @Column(nullable = false, length = 20)
    private String proFechaIngreso;

    @Column(nullable = false)
    private BigDecimal proPrecio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 100)
    private String proMarca;

    @Column(nullable = false, length = 100)
    private String proColor;

    @Column(nullable = false, length = 50)
    private String proGenero;

    @Column(nullable = false, length = 50)
    private String proEdad;

    @ManyToOne
    @JoinColumn(name = "talla_id", nullable = false)
    private Talla talla;

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;
}
