package com.example.venta_sneackers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.PedidoService;

import com.example.venta_sneackers.dto.PedidoDetalleResponseDTO;

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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/V1/detalle-pedidos")
@RequiredArgsConstructor

@Tag(name = "Detalle Pedido", description = "Operaciones relacionadas con los Detalles de Pedido")

public class DetallePedidoController {

    private final PedidoService pedidoService;


    ////////////////////////////////////////////////////////////////////
    //////////////////////////////////GETS//////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // OBTENER TODOS LOS DETALLES DE PEDIDO
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los detalles de pedido", description = "Obtiene todos los detalles de pedido disponibles en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles de pedido obtenidos exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = PedidoDetalleResponseDTO.class),
                    examples = @ExampleObject(value = """

                                {
                                    "detallePedidoId": 1,
                                    "pedidoId": 101,
                                    "pedidoNombre": "PED-101",
                                    "clienteId": 1001,
                                    "clienteNombre": "Juan Pérez",
                                    "productoId": 501,
                                    "productoNombre": "Sneaker Modelo A",
                                    "cantidad": 2
                                }
                            """))),
        @ApiResponse(responseCode = "404", description = "No se encontraron detalles de pedido en el sistema",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontraron detalles de pedido en el sistema."
                                    }
                                    """)))
    })

        
    public ResponseEntity<?> obtenerTodosLosDetallesPedido() {
        List<PedidoDetalleResponseDTO> detallesPedido = pedidoService.obtenerTodosDetalles();
        if (!detallesPedido.isEmpty()) {
            return ResponseEntity.ok(CollectionModel.of(
                    detallesPedido,
                    linkTo(methodOn(DetallePedidoController.class).obtenerTodosLosDetallesPedido()).withSelfRel(),
                    linkTo(methodOn(DetallePedidoController.class).obtenerDetallePedidoPorIdPedido(null)).withRel("buscarPorPedido"),
                    linkTo(methodOn(DetallePedidoController.class).obtenerDetallePedidoPorIdProducto(null)).withRel("buscarPorProducto")
            ));
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron detalles de pedido en el sistema."
            );
            return ResponseEntity.status(404).body(error);
        }
    }

    // OBTENER DETALLE PEDIDO POR ID DE PEDIDO
    @GetMapping(value = "/pedido/{pedidoId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener detalles de pedido por ID de pedido", description = "Obtiene los detalles de un pedido específico utilizando el ID del pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles encontrados exitosamente",
                content = @Content(mediaType = "application/hal+json",
                    schema = @Schema(implementation = PedidoDetalleResponseDTO.class),
                    examples = @ExampleObject(value = """
                            {
                                "detallePedidoId": 1,
                                "pedidoId": 101,
                                "pedidoNombre": "PED-101",
                                "clienteId": 1001,
                                "clienteNombre": "Juan Pérez",
                                "productoId": 501,
                                "productoNombre": "Sneaker Modelo A",
                                "cantidad": 2
                            }
                    """))),

            @ApiResponse(responseCode = "404", description = "No se encontraron detalles de pedido para el ID de pedido proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron detalles de pedido para el ID de pedido proporcionado."
                }
            """)))
    })


    public ResponseEntity<?> obtenerDetallePedidoPorIdPedido(@PathVariable Long pedidoId) {
        List<PedidoDetalleResponseDTO> detallesPedido = pedidoService.obtenerDetallesPorPedidoId(pedidoId);
        if (!detallesPedido.isEmpty()) {
            return ResponseEntity.ok(CollectionModel.of(
                    detallesPedido,
                    linkTo(methodOn(DetallePedidoController.class).obtenerDetallePedidoPorIdPedido(pedidoId)).withSelfRel(),
                    linkTo(methodOn(DetallePedidoController.class).obtenerTodosLosDetallesPedido()).withRel("todosLosDetalles"),
                    linkTo(methodOn(DetallePedidoController.class).obtenerDetallePedidoPorIdProducto(null)).withRel("buscarPorProducto")
            ));
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron detalles de pedido para el ID proporcionado."
            );
            return ResponseEntity.status(404).body(error);
        }
    }

    // OBTENER DETALLE PEDIDO POR ID DE PRODUCTO
    @GetMapping(value = "/producto/{productoId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener detalles de pedido por ID de producto", description = "Obtiene los detalles de pedido asociados a un producto específico utilizando el ID del producto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles encontrados exitosamente",
                content = @Content(mediaType = "application/hal+json",
                    schema = @Schema(implementation = PedidoDetalleResponseDTO.class),
                    examples = @ExampleObject(value = """
                            {
                                "detallePedidoId": 1,
                                "pedidoId": 101,
                                "pedidoNombre": "PED-101",
                                "clienteId": 1001,
                                "clienteNombre": "Juan Pérez",
                                "productoId": 501,
                                "productoNombre": "Sneaker Modelo A",
                                "cantidad": 2
                            }
                    """))),

            @ApiResponse(responseCode = "404", description = "No se encontraron detalles de pedido para el ID de producto proporcionado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Map.class),
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron detalles de pedido para el ID de producto proporcionado."
                }
            """)))
    })    
    public ResponseEntity<?> obtenerDetallePedidoPorIdProducto(@PathVariable Long productoId) {
        List<PedidoDetalleResponseDTO> detallesPedido = pedidoService.obtenerDetallesPorProductoId(productoId);
        if (!detallesPedido.isEmpty()) {
            return ResponseEntity.ok(CollectionModel.of(
                    detallesPedido,
                    linkTo(methodOn(DetallePedidoController.class).obtenerDetallePedidoPorIdProducto(productoId)).withSelfRel(),
                    linkTo(methodOn(DetallePedidoController.class).obtenerTodosLosDetallesPedido()).withRel("todosLosDetalles"),
                    linkTo(methodOn(DetallePedidoController.class).obtenerDetallePedidoPorIdPedido(null)).withRel("buscarPorPedido")
            ));
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron detalles de pedido para el ID de producto proporcionado."
            );
            return ResponseEntity.status(404).body(error);
        }
    }
    
}
