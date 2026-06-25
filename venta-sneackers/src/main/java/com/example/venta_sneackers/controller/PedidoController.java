package com.example.venta_sneackers.controller;

import com.example.venta_sneackers.dto.PedidoRequestDTO;
import com.example.venta_sneackers.dto.PedidoResponseDTO;
import com.example.venta_sneackers.Service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/V1/pedidos")
@Validated
@RequiredArgsConstructor
@Tag(name = "Pedido", description = "Operaciones relacionadas con los Pedidos")
@SuppressWarnings("null")
public class PedidoController {

    private final PedidoService pedidoService;

    


    ////////////////////////////////////////////////////////////////////
    //////////////////////////////////GETS//////////////////////////////
    ////////////////////////////////////////////////////////////////////


    // GET - Obtener todos los pedidos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene la lista de todos los pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos", content = @Content(mediaType = "application/hal+json")),
            @ApiResponse(responseCode = "404", description = "No hay pedidos en el sistema")
    })
    public ResponseEntity<?> obtenerTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.obtenerTodos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No hay pedidos"));
        }

        List<EntityModel<PedidoResponseDTO>> resources = pedidos.stream()
                .map(this::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(
                resources,
                linkTo(methodOn(PedidoController.class).obtenerTodos()).withSelfRel(),
                linkTo(methodOn(PedidoController.class).obtenerPorId(null)).withRel("buscarPorId"),
                linkTo(methodOn(PedidoController.class).buscarPorTotal(0d)).withRel("buscarPorTotal")
        ));
    }


    // GET OBTENER PEDIDO POR ID
    @GetMapping(value = "/buscar/total/{pedTotal}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar por total", description = "Busca pedidos por total de compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = @Content(mediaType = "application/hal+json")),
            @ApiResponse(responseCode = "404", description = "No hay pedidos con ese total")
    })
    public ResponseEntity<?> buscarPorTotal(@PathVariable double pedTotal) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorTotalDeCompra(pedTotal).stream()
            .map(p -> pedidoService.obtenerPorId(p.getIdPedido()))
                .toList();
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No hay pedidos"));
        }

        List<EntityModel<PedidoResponseDTO>> resources = pedidos.stream()
            .map(this::toModel)
            .toList();

        return ResponseEntity.ok(CollectionModel.of(
            resources,
            linkTo(methodOn(PedidoController.class).buscarPorTotal(pedTotal)).withSelfRel(),
            linkTo(methodOn(PedidoController.class).obtenerTodos()).withRel("pedidos")
        ));
    }

        private EntityModel<PedidoResponseDTO> toModel(PedidoResponseDTO pedido) {
        return EntityModel.of(
            pedido,
            linkTo(methodOn(PedidoController.class).obtenerPorId(pedido.getIdPedido())).withSelfRel()
        );
        }


    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener pedido por ID", description = "Obtiene un pedido específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = @Content(mediaType = "application/hal+json")),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.obtenerPorId(id);
            return ResponseEntity.ok(toModel(pedido));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    ////////////////////////////////////////////////////////////////////
    //////////////////////////////////POST//////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // POST - Crear un nuevo pedido
    @PostMapping
    @Operation(summary = "Crear pedido", description = "Crea un nuevo pedido con detalles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> guardar(@Valid @RequestBody PedidoRequestDTO requestDTO) {
        try {
            PedidoResponseDTO pedido = pedidoService.guardar(requestDTO);
            return ResponseEntity.status(201).body(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    ////////////////////////////////////////////////////////////////////
    //////////////////////////////////PUT//////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // PUT - Actualizar un pedido existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO requestDTO) {
        try {
            PedidoResponseDTO pedido = pedidoService.actualizar(id, requestDTO);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }




    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////DELETE//////////////////////////////
    ////////////////////////////////////////////////////////////////////
    
    // DELETE - Eliminar un pedido existente
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    

    ////////////////////////////////////////////////////////////////////
    //////////////////////////////////PATCH//////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // PATCH - Actualizar el estado de pago de un pedido
    @PatchMapping("/{id}/pago")
    @Operation(summary = "Actualizar estado de pago", description = "Actualiza si un pedido está pagado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<?> actualizarEstadoPago(@PathVariable Long id, @RequestParam boolean pagado) {
        try {
            pedidoService.actualizarEstadoPago(id, pagado);
            PedidoResponseDTO pedido = pedidoService.obtenerPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
