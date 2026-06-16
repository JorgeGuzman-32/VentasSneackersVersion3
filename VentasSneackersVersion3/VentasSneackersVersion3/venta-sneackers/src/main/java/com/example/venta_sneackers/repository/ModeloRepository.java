package com.example.venta_sneackers.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.venta_sneackers.model.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    // TIPO 1: QUERY METHODS 

    // → SELECT * FROM modelo WHERE UPPER(mod_nombre) LIKE UPPER('%?%')
    List<Modelo> findByModNombreContainingIgnoreCase(String modNombre);

    // → SELECT * FROM modelo WHERE mod_nombre = ?
    List<Modelo> findByModNombre(String modNombre);

    // → SELECT * FROM modelo WHERE mod_temporada = ?
    List<Modelo> findByModTemporada(String modTemporada);

    // → SELECT * FROM modelo WHERE mod_anio_lanzamiento = ?
    List<Modelo> findByModAnioLanzamiento(Integer modAnioLanzamiento);

    // → SELECT * FROM modelo WHERE mod_anio_lanzamiento > ?
    List<Modelo> findByModAnioLanzamientoGreaterThan(Integer modAnio);

    // → SELECT * FROM modelo WHERE mod_anio_lanzamiento BETWEEN ? AND ?
    List<Modelo> findByModAnioLanzamientoBetween(Integer modAnioInicio, Integer modAnioFin);

    // → SELECT * FROM modelo WHERE mod_edicion_limitada = ?
    List<Modelo> findByModEdicionLimitada(Boolean modEdicionLimitada);

    // → SELECT * FROM modelo WHERE UPPER(mod_descripcion) LIKE UPPER('%?%')
    List<Modelo> findByModDescripcionContainingIgnoreCase(String modDescripcion);

    // TIPO 2: @QUERY CON JPQL 

    // Buscar modelos por temporada ordenados por anio de lanzamiento
    @Query("SELECT m FROM Modelo m WHERE m.modTemporada = :temporada ORDER BY m.modAnioLanzamiento DESC")
    List<Modelo> findByTemporadaOrdenado(@Param("temporada") String temporada);

    // Buscar modelos de edicion limitada por temporada
    @Query("SELECT m FROM Modelo m WHERE m.modEdicionLimitada = true AND m.modTemporada = :temporada")
    List<Modelo> findEdicionLimitadaByTemporada(@Param("temporada") String temporada);

    // Buscar modelos lanzados despues de un anio especifico
    @Query("SELECT m FROM Modelo m WHERE m.modAnioLanzamiento >= :anio ORDER BY m.modAnioLanzamiento ASC")
    List<Modelo> findModelosDesdeAnio(@Param("anio") Integer anio);

    // Buscar modelos que NO son edicion limitada
    @Query("SELECT m FROM Modelo m WHERE m.modEdicionLimitada = false")
    List<Modelo> findModelosNoEdicionLimitada();

    // Buscar modelos por nombre y temporada a la vez
    @Query("SELECT m FROM Modelo m WHERE m.modNombre = :nombre AND m.modTemporada = :temporada")
    List<Modelo> findByNombreAndTemporada(@Param("nombre") String nombre, @Param("temporada") String temporada);

    // Buscar modelos ordenados por nombre alfabeticamente
    @Query("SELECT m FROM Modelo m ORDER BY m.modNombre ASC")
    List<Modelo> findAllOrderByNombreAsc();

    // Buscar modelos lanzados antes de un anio especifico
    @Query("SELECT m FROM Modelo m WHERE m.modAnioLanzamiento < :anio ORDER BY m.modAnioLanzamiento DESC")
    List<Modelo> findModelosAntesDeAnio(@Param("anio") Integer anio);

}
