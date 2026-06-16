package com.example.venta_sneackers.controller;

import java.util.List;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.venta_sneackers.Service.TallaService;
import com.example.venta_sneackers.dto.TallaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@Tag(name = "Talla", description = "Endpoints para gestionar las tallas de los productos")
@RequestMapping("/api/V1/tallas")
@RequiredArgsConstructor


public class TallaController {

    private final TallaService tallaService;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping
    @Operation(summary = "Obtener todas las tallas", description = "Devuelve una lista de todas las tallas disponibles")
    public ResponseEntity<List<TallaResponseDTO>> obtenerTodos() {
    return ResponseEntity.ok(tallaService.obtenerTodos());
    }

    //Buscar por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener talla por ID", description = "Devuelve una talla específica según su ID")
    public ResponseEntity<TallaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return tallaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    @PostMapping
    @Operation(summary = "Crear una nueva talla", description = "Permite crear una nueva talla para los productos")
    public String postMethodName(@RequestBody String entity) {
        return entity;
    }


    //Actualizar por ID
    @PutMapping("/{id}") 
    @Operation(summary = "Actualizar talla por ID", description = "Permite actualizar los datos de una talla específica según su ID")   
    public ResponseEntity<TallaResponseDTO> actualizar(@PathVariable Long id, @RequestBody TallaResponseDTO dto) {
        try {
            TallaResponseDTO updated = tallaService.actualizar(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar talla", description = "Permite eliminar una talla específica según su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tallaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    
        
}
