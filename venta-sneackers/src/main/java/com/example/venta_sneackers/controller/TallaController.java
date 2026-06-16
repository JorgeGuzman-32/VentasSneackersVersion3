package com.example.venta_sneackers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.TallaService;
import com.example.venta_sneackers.dto.TallaRequestDTO;
import com.example.venta_sneackers.dto.TallaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Talla", description = "Endpoints para gestionar las tallas de los productos")
@RequestMapping("/api/V1/tallas")
@RequiredArgsConstructor


public class TallaController {

    private final TallaService tallaService;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //OBTENER TODAS LAS TALLAS
    @GetMapping
    @Operation(summary = "Obtener todas las tallas", description = "Devuelve una lista de todas las tallas disponibles para los productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tallas obtenidas exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TallaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tallas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    public ResponseEntity<List<TallaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(tallaService.obtenerTodos());
    }

    //OBTENER TALLA POR ID  
    @GetMapping("/{id}")
    @Operation(summary = "Obtener talla por ID", description = "Devuelve una talla específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Talla obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TallaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Talla no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TallaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return tallaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    //CREAR NUEVA TALLA
    @PostMapping
    @Operation(summary = "Crear una nueva talla", description = "Permite crear una nueva talla para los productos utilizando los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Talla creada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TallaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TallaResponseDTO> crear(@RequestBody TallaRequestDTO dto) {
        TallaResponseDTO created = tallaService.guardar(dto);
        return ResponseEntity.status(201).body(created);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUSTs                 ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    //ACTUALIZAR TALLA POR ID
    @PutMapping("/{id}") 
    @Operation(summary = "Actualizar talla por ID", description = "Permite actualizar los datos de una talla específica según su ID")   
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Talla actualizada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TallaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Talla no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    public ResponseEntity<TallaResponseDTO> actualizar(@PathVariable Long id, @RequestBody TallaResponseDTO dto) {
        try {
            TallaResponseDTO updated = tallaService.actualizar(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                 //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////// 

    //ELIMINAR TALLA POR ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar talla", description = "Permite eliminar una talla específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Talla eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Talla no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })  

    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tallaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }   
}
