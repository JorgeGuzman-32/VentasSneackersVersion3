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
import java.util.Map;
import java.util.Optional;
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
    ///////////////////////////                 GETs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    /// Obtener todos los modelos
    @GetMapping
    @Operation(summary = "Obtener todos los modelos", description = "Devuelve una lista de todos los modelos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ModeloResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron modelos",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron modelos registrados en el sistema."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno del servidor al procesar la solicitud."
                }
            """)))
    })
    public ResponseEntity<?> obtenerTodos() {
        List<ModeloResponseDTO> modelos = modeloService.obtenerTodos();
        if (modelos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron modelos registrados en el sistema."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(modelos);
    }



    /// Obtener un modelo por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener modelo por ID", description = "Devuelve un modelo específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Modelo no encontrado con el ID proporcionado."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno del servidor."
                }
            """)))
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<ModeloResponseDTO> modeloOpt = modeloService.obtenerPorId(id);
        if (modeloOpt.isPresent()) {
            return ResponseEntity.ok(modeloOpt.get());
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Modelo no encontrado con el ID proporcionado."
            );
            return ResponseEntity.status(404).body(error);
        }
    }


    /// Buscar modelos por nombre
    @GetMapping("/buscar/nombre/{modNombre}")
    @Operation(summary = "Buscar modelo por nombre", description = "Devuelve los modelos que coinciden con el nombre proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelos encontrados exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ModeloResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron modelos para el nombre especificado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron modelos con el nombre especificado."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno del servidor."
                }
            """)))
    })
    public ResponseEntity<?> buscarPorNombre(@PathVariable String modNombre) {
        List<ModeloResponseDTO> modelos = modeloService.buscarPorNombre(modNombre);
        if (modelos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron modelos con el nombre especificado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(modelos);
    }
    
    /// Buscar modelos por temporada
    @GetMapping("/buscar/temporada/{modTemporada}")
    @Operation(summary = "Buscar modelo por temporada", description = "Devuelve los modelos que pertenecen a la temporada especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelos encontrados exitosamente",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ModeloResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron modelos para la temporada especificada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron modelos para la temporada especificada."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno del servidor."
                }
            """)))
    })
    public ResponseEntity<?> buscarPorTemporada(@PathVariable String modTemporada) {
        List<ModeloResponseDTO> modelos = modeloService.buscarPorTemporada(modTemporada);
        if (modelos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron modelos para la temporada especificada."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(modelos);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////    
    ///////////////////////////                 POSTs                                ////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /// Crear un nuevo modelo
    @PostMapping
    @Operation(summary = "Crear un nuevo modelo", description = "Permite crear un nuevo modelo de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Modelo creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class),
                examples = @ExampleObject(name = "EjemploModelo", value = 
                "{\"modNombre\": \"Air Max\",\"modTemporada\": \"Verano\", \"modAnioLanzamiento\": 2024, \"modEdicionLimitada\": false, \"modDescripcion\": \"Zapatilla deportiva de alto rendimiento\"}"))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Los datos proporcionados para el modelo no son válidos."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno al intentar registrar el modelo."
                }
            """)))
    })
    public ResponseEntity<ModeloResponseDTO> crearModelo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Modelo a crear",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModeloRequestDTO.class),
                            examples = @ExampleObject(name = "EjemploModelo",
                            value = "{\"modNombre\": \"Air Max\", \"modTemporada\": \"Verano\", \"modAnioLanzamiento\": 2024, \"modEdicionLimitada\": false, \"modDescripcion\": \"Zapatilla deportiva de alto rendimiento\"}")))
            @RequestBody ModeloRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.guardar(dto));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////                 PUTs                                ///////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /// Actualizar un modelo existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar modelo por ID", description = "Permite actualizar los datos de un modelo específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modelo actualizado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Los datos de actualización proporcionados no son válidos."
                }
            """))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontró el modelo especificado para actualizar."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno del servidor."
                }
            """)))
    })
    public ResponseEntity<?> actualizarModelo(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Modelo con los datos actualizados",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModeloRequestDTO.class)))
            @RequestBody ModeloRequestDTO dto) {
        Optional<ModeloResponseDTO> actualizadoOpt = modeloService.actualizar(id, dto);
        if (actualizadoOpt.isPresent()) {
            return ResponseEntity.ok(actualizadoOpt.get());
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontró el modelo especificado para actualizar."
            );
            return ResponseEntity.status(404).body(error);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////                 DELETEs                             /////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /// Eliminar un modelo por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar modelo por ID", description = "Permite eliminar un modelo específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Modelo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "El modelo que intenta eliminar no existe."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno al intentar eliminar el modelo."
                }
            """)))
    })
    public ResponseEntity<?> eliminarModelo(@PathVariable Long id) {
        modeloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
