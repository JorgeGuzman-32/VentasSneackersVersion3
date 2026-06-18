package com.example.venta_sneackers.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.PedidoService;
import com.example.venta_sneackers.dto.PedidoRequestDTO;
import com.example.venta_sneackers.dto.PedidoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/V1/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gestionar los pedidos de la tienda de sneakers.")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Obtener todos los pedidos
    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron pedidos registrados en el sistema."
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
    public ResponseEntity<?> obtenerTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.obtenerTodos();
        if (pedidos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron pedidos registrados en el sistema."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(pedidos);
    }


    //Obtener un pedido por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve los detalles de un pedido específico utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido obtenido exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Pedido no encontrado."
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
        Optional<PedidoResponseDTO> pedidoOpt = pedidoService.obtenerPorId(id);
        if (pedidoOpt.isPresent()) {
            return ResponseEntity.ok(pedidoOpt.get());
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Pedido no encontrado con el ID proporcionado."
            );
            return ResponseEntity.status(404).body(error);
        }
    }


    //Buscar pedidos por estado de pago
    @GetMapping("/buscar/pagado/{pedPagado}")
    @Operation(summary = "Buscar pedidos por estado de pago", description = "Devuelve una lista de pedidos filtrados por su estado de pago (pagado o no pagado).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos con el estado de pago especificado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron pedidos con el estado de pago especificado."
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
    public ResponseEntity<?> buscarPorPagado(@PathVariable boolean pedPagado) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorPagado(pedPagado);
        if (pedidos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron pedidos con el estado de pago especificado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(pedidos);
    }

    //Buscar pedidos por ID de cliente
    @GetMapping("/buscar/cliente/{clienteId}")
    @Operation(summary = "Buscar pedidos por ID de cliente", description = "Devuelve una lista de pedidos asociados a un cliente específico utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para el cliente especificado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron pedidos para el cliente especificado."
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
    public ResponseEntity<?> buscarPorClienteId(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.obtenerPorClienteId(clienteId);
        if (pedidos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron pedidos para el cliente especificado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(pedidos);
    }


    //Buscar pedidos por ID de producto
    @GetMapping("/buscar/producto/{productoId}")
    @Operation(summary = "Buscar pedidos por ID de producto", description = "Este endpoint aún no está disponible porque la entidad Pedido no tiene relación con Producto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "501", description = "Funcionalidad no implementada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No implementado",
                    "descripcion": "Funcionalidad no implementada."
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
    public ResponseEntity<?> buscarPorProductoId(@PathVariable Long productoId) {
        Map<String, String> error = Map.of(
            "error", "No implementado",
            "descripcion", "Funcionalidad no implementada."
        );
        return ResponseEntity.status(501).body(error);
    }


    //Buscar pedidos por fecha de COMPRA
    @GetMapping("/buscar/fecha/{fechaCreacion}")
    @Operation(summary = "Buscar pedidos por fecha de creación", description = "Devuelve una lista de pedidos creados en una fecha específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para la fecha especificada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron pedidos para la fecha especificada."
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
    public ResponseEntity<?> buscarPorFechaCreacion(@PathVariable String fechaCreacion) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorFechaCompra(fechaCreacion);
        if (pedidos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron pedidos para la fecha especificada."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(pedidos);
    }   


    ///Buscar pedidos por el total del pedido
    @GetMapping("/buscar/total/{pedTotal}")
    @Operation(summary = "Buscar pedidos por total", description = "Devuelve una lista de pedidos que tienen un total específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para el total especificado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron pedidos para el total especificado."
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
    public ResponseEntity<?> buscarPorTotal(@PathVariable double pedTotal) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPorTotalDeCompra(pedTotal);
        if (pedidos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron pedidos para el total especificado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(pedidos);
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                                /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///Crear un nuevo pedido
    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Permite crear un nuevo pedido en el sistema utilizando los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Verifique los datos de la solicitud."
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
    public ResponseEntity<PedidoResponseDTO> guardar(@RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.guardar(dto));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Actualizar un pedido existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido existente", description = "Permite actualizar los detalles de un pedido existente utilizando su ID y los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Verifique los datos de la solicitud."
                }
            """))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Pedido no encontrado."
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
    public ResponseEntity<?> actualizar(@PathVariable Long id, 
        @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO actualizado = pedidoService.actualizar(id, dto);
        if (actualizado == null) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Pedido no encontrado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(actualizado);
    }

    //Actualizar el estado de pago de un pedido
    @PutMapping("/{id}/pagado")
    @Operation(summary = "Actualizar el estado de pago de un pedido", description = "Permite actualizar el estado de pago de un pedido específico utilizando su ID y el nuevo estado de pago proporcionado en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de pago actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Verifique el estado de pago enviado."
                }
            """))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Pedido no encontrado."
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
    public ResponseEntity<?> actualizarPagado(@PathVariable Long id, @RequestBody boolean pedPagado) {
        PedidoResponseDTO actualizado = pedidoService.actualizarEstadoPago(id, pedPagado);
        if (actualizado == null) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Pedido no encontrado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(actualizado);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                               //////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Eliminar un pedido por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Permite eliminar un pedido específico utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Pedido no encontrado."
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
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}