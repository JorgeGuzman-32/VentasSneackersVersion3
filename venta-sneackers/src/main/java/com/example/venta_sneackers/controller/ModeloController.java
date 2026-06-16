package com.example.venta_sneackers.controller;

import com.example.venta_sneackers.Service.ModeloService;
import com.example.venta_sneackers.dto.ModeloRequestDTO;
import com.example.venta_sneackers.dto.ModeloResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RestController
@Tag(name = "Modelo", description = "Endpoints relacionados con los modelos de productos")
@RequestMapping("/api/V1/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloService modeloService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////    
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    /// Obtener todos los modelos
    @GetMapping
    @Operation(summary = "Obtener todos los modelos", description = "Devuelve una lista de todos los modelos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ModeloResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron modelos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModeloResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(modeloService.obtenerTodos());
    }



    /// Obtener un modelo por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener modelo por ID", description = "Devuelve un modelo específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModeloResponseDTO> obtenerPorId(@PathVariable Long id) {
        return modeloService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /// Buscar modelos por nombre
    @GetMapping("/buscar/nombre/{modNombre}")
    @Operation(summary = "Buscar modelo por nombre", description = "Devuelve los modelos que coinciden con el nombre proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelos encontrados exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ModeloResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron modelos para el nombre especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModeloResponseDTO>> buscarPorNombre(@PathVariable String modNombre) {
        return ResponseEntity.ok(modeloService.buscarPorNombre(modNombre));
    }
    
    /// Buscar modelos por temporada
    @GetMapping("/buscar/temporada/{modTemporada}")
    @Operation(summary = "Buscar modelo por temporada", description = "Devuelve los modelos que pertenecen a la temporada especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelos encontrados exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ModeloResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron modelos para la temporada especificada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")

    })
    public ResponseEntity<List<ModeloResponseDTO>> buscarPorTemporada(@PathVariable String modTemporada) {
        return ResponseEntity.ok(modeloService.buscarPorTemporada(modTemporada));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////    
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /// Crear un nuevo modelo
    @PostMapping
    @Operation(summary = "Crear un nuevo modelo", description = "Permite crear un nuevo modelo de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Modelo creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class),
                examples = @ExampleObject(name = "EjemploModelo", value = "{\"modNombre\": \"Air Max\", \"modTemporada\": \"Verano\", \"modAnioLanzamiento\": 2024, \"modEdicionLimitada\": false, \"modDescripcion\": \"Zapatilla deportiva de alto rendimiento\"}"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModeloResponseDTO> crearModelo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Modelo a crear",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModeloRequestDTO.class),
                            examples = @ExampleObject(name = "EjemploModelo", value = "{\"modNombre\": \"Air Max\", \"modTemporada\": \"Verano\", \"modAnioLanzamiento\": 2024, \"modEdicionLimitada\": false, \"modDescripcion\": \"Zapatilla deportiva de alto rendimiento\"}")))
            @RequestBody ModeloRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.guardar(dto));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////                 PUTs                 ///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /// Actualizar un modelo existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar modelo por ID", description = "Permite actualizar los datos de un modelo específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo actualizado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModeloResponseDTO> actualizarModelo(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Modelo con los datos actualizados",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModeloRequestDTO.class)))
            @RequestBody ModeloRequestDTO dto) {
        return modeloService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////                 DELETEs                 /////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /// Eliminar un modelo por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar modelo por ID", description = "Permite eliminar un modelo específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo eliminado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarModelo(@PathVariable Long id) {
        modeloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
