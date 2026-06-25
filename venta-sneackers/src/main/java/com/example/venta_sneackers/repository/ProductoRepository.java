package com.example.venta_sneackers.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.venta_sneackers.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {


    List<Producto> findByProNombreContainingIgnoreCase(String nombre);

    List<Producto> findByProMarca(String marca);

    List<Producto> findByProColor(String color);

    List<Producto> findByProGenero(String genero);

    List<Producto> findByStockGreaterThan(Integer stock);

    List<Producto> findByStockLessThan(Integer stock);

    List<Producto> findByProPrecioBetween(BigDecimal min, BigDecimal max);

    @Query("SELECT p FROM Producto p WHERE p.proPrecio <= :precioMax ORDER BY p.proPrecio ASC")
    List<Producto> findProductosBajoPresupuesto(@Param("precioMax") BigDecimal precioMax);

    @Query("SELECT p FROM Producto p WHERE p.modelo.idModelo = :modeloId")
    List<Producto> findByModeloId(@Param("modeloId") Long modeloId);

    @Query("SELECT p FROM Producto p WHERE p.talla.Id = :tallaId")
    List<Producto> findByTallaId(@Param("tallaId") Long tallaId);

    @Query("SELECT p FROM Producto p ORDER BY p.proPrecio DESC")
    List<Producto> findAllOrderByPrecioDesc();

    @Query("SELECT p FROM Producto p WHERE p.proMarca = :marca AND p.stock > 0")
    List<Producto> findByMarcaConStock(@Param("marca") String marca);

    @Query("SELECT p FROM Producto p WHERE p.modelo.modTemporada = :temporada")
    List<Producto> findByTemporadaModelo(@Param("temporada") String temporada);


    //TIPO 3: SQL 
    //resultados ordenados por precio de menor a mayor, filtrando por un precio maximo dado por el usuario
    @Query(
        value = "SELECT * FROM productos WHERE pro_precio <= : precioMax ORDER BY pro_precio ASC",
        nativeQuery = true
    )
    List<Producto> findProductosBajoPresupuestoSQL(@Param("precioMax") BigDecimal precioMax);
    //resulrados ordenados por precio de mayor a menor, filtrando por un precio minimo dado por el usuario
    @Query(
        value = "SELECT * FROM productos WHERE pro_precio >= : precioMin ORDER BY pro_precio DESC",
        nativeQuery = true
    )
    List<Producto> findProductosSobrePresupuestoSQL(@Param("precioMin") BigDecimal precioMin);

    
}
