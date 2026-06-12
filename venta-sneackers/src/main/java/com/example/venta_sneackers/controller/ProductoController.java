package com.example.venta_sneackers.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.ProductoService;
import com.example.venta_sneackers.dto.ProductoRequestDTO;
import com.example.venta_sneackers.dto.ProductoResponseDTO;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // GETs

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/{proNombre}")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@PathVariable String proNombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(proNombre));
    }

    @GetMapping("/buscar/marca/{proMarca}")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorMarca(@PathVariable String proMarca) {
        return ResponseEntity.ok(productoService.buscarPorMarca(proMarca));
    }   
    
    @GetMapping("/buscar/color/{proColor}")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorColor(@PathVariable String proColor) {
        return ResponseEntity.ok(productoService.proColor(proColor));
    }           

    @GetMapping("/buscar/genero/{proGenero}")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorGenero(@PathVariable String proGenero) {
        return ResponseEntity.ok(productoService.proGenero(proGenero));

    }   

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, Integer nuevoStock) {    
        boolean actualizado = productoService.actualizarStock(id, nuevoStock);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }       

    @PutMapping("/{id}/precio")
    public ResponseEntity<Void> actualizarPrecio(@PathVariable Long id, BigDecimal nuevoPrecio) {    
        boolean actualizado = productoService.actualizarPrecio(id, nuevoPrecio);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }       
       
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO dto)   {
        ProductoResponseDTO created = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);     

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {           
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
}
