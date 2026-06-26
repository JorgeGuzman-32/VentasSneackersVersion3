package com.example.venta_sneackers.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.venta_sneackers.Service.TallaService;
import com.example.venta_sneackers.dto.TallaRequestDTO;
import com.example.venta_sneackers.dto.TallaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Controlador REST para gestionar operaciones CRUD de Talla
@RestController
@Tag(name = "Talla", description = "Endpoints para gestionar las tallas de los productos")
@RequestMapping("/api/V1/tallas")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class TallaController {

    private final TallaService tallaService;


    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////    GETS    //////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // GET - Obtener todas las tallas disponibles
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las tallas", description = "Devuelve una lista de todas las tallas disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tallas obtenida exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                        schema = @Schema(implementation = TallaResponseDTO.class),
                        examples = @ExampleObject(
                            value = "[{\"id\":1,\"tallNombre\":\"42\",\"unidad\":\"EU\"}]"
                        )
                    )),
            @ApiResponse(responseCode = "404", description = "No se encontraron tallas",
                    content = @Content(mediaType = "application/json"))
    })
        
    public ResponseEntity<CollectionModel<EntityModel<TallaResponseDTO>>> obtenerTodas() {
        List<TallaResponseDTO> tallas = tallaService.obtenerTodas();
        if (tallas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<TallaResponseDTO>> resources = tallas.stream()
                .map(this::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                resources,
                linkTo(methodOn(TallaController.class).obtenerTodas()).withSelfRel(),
                linkTo(methodOn(TallaController.class).obtenerPorId(null)).withRel("buscarPorId")
        ));
    }

    // GET - Obtener una talla por ID
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener una talla por ID", description = "Busca y devuelve una talla específica por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Talla encontrada exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                        schema = @Schema(implementation = TallaResponseDTO.class),
                        examples = @ExampleObject(
                            value = "{\"id\":1,\"tallNombre\":\"42\",\"unidad\":\"EU\"}"
                        )
                    )),
            @ApiResponse(responseCode = "404", description = "Talla no encontrada",
                    content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Map.class),
                        examples = @ExampleObject(
                            value = "{\"error\":\"Talla no encontrada\"}"
                        )
                    ))
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<TallaResponseDTO> talla = tallaService.obtenerPorId(id);
        if (talla.isPresent()) {
            return ResponseEntity.ok(toModel(talla.get()));
        }
        return ResponseEntity.status(404).body(Map.of("error", "Talla no encontrada"));
    }

    private EntityModel<TallaResponseDTO> toModel(TallaResponseDTO talla) {
        return EntityModel.of(
                talla,
                linkTo(methodOn(TallaController.class).obtenerPorId(talla.getId())).withSelfRel()
        );
    }



    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////    POST    //////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // POST - Guardar una nueva talla
    @PostMapping("/guardar")
    @Operation(summary = "Guardar una nueva talla", description = "Crea una nueva talla en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Talla creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TallaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> guardar(@RequestBody TallaRequestDTO dto) {
        try {
            TallaResponseDTO talla = tallaService.guardar(dto);
            return ResponseEntity.status(201).body(talla);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }



    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////////    PUT     //////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // PUT - Actualizar una talla existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una talla", description = "Actualiza los datos de una talla existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Talla actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TallaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Talla no encontrada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TallaRequestDTO dto) {
        try {
            TallaResponseDTO talla = tallaService.actualizar(id, dto);
            return ResponseEntity.ok(talla);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }



    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////    DELETE    //////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // DELETE - Eliminar una talla
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una talla", description = "Elimina una talla específica de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Talla eliminada exitosamente",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Void.class),
                    examples = @ExampleObject(
                        value = "Talla eliminada exitosamente"
                    )
                )
            ),
            @ApiResponse(responseCode = "404", description = "Talla no encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            tallaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}