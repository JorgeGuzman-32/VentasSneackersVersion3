package com.example.venta_sneackers.controller;

import java.util.List;
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
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/V1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Obtener todos los pedidos
    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }


    //Obtener un pedido por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve los detalles de un pedido específico utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido obtenido exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Optional<PedidoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }


    //Buscar pedidos por estado de pago
    @GetMapping("/buscar/pagado/{pedPagado}")
    @Operation(summary = "Buscar pedidos por estado de pago", description = "Devuelve una lista de pedidos filtrados por su estado de pago (pagado o no pagado).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos con el estado de pago especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorPagado(@PathVariable boolean pedPagado) {
        return ResponseEntity.ok(pedidoService.buscarPorPagado(pedPagado));
    }

    //Buscar pedidos por ID de cliente
    @GetMapping("/buscar/cliente/{clienteId}")
    @Operation(summary = "Buscar pedidos por ID de cliente", description = "Devuelve una lista de pedidos asociados a un cliente específico utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para el cliente especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorClienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.obtenerPorClienteId(clienteId));
    }


    //Buscar pedidos por ID de producto
    @GetMapping("/buscar/producto/{productoId}")
    @Operation(summary = "Buscar pedidos por ID de producto", description = "Este endpoint aún no está disponible porque la entidad Pedido no tiene relación con Producto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "501", description = "Funcionalidad no implementada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> buscarPorProductoId(@PathVariable Long productoId) {
        return ResponseEntity.status(501).build();
    }


    //Buscar pedidos por fecha de COMPRA
    @GetMapping("/buscar/fecha/{fechaCreacion}")
    @Operation(summary = "Buscar pedidos por fecha de creación", description = "Devuelve una lista de pedidos creados en una fecha específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para la fecha especificada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorFechaCreacion(@PathVariable String fechaCreacion) {
        return ResponseEntity.ok(pedidoService.buscarPorFechaCompra(fechaCreacion));
    }   


    ///Buscar pedidos por el total del pedido
    @GetMapping("/buscar/total/{pedTotal}")
    @Operation(summary = "Buscar pedidos por total", description = "Devuelve una lista de pedidos que tienen un total específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para el total especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorTotal(@PathVariable double pedTotal) {
        return ResponseEntity.ok(pedidoService.buscarPorTotalDeCompra(pedTotal));
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///Crear un nuevo pedido
    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Permite crear un nuevo pedido en el sistema utilizando los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PedidoResponseDTO> guardar(@RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.guardar(dto));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Actualizar un pedido existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido existente", description = "Permite actualizar los detalles de un pedido existente utilizando su ID y los datos proporcionados en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PedidoResponseDTO> actualizar(@PathVariable Long id, 
        @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.ok(pedidoService.actualizar(id, dto));
    }

    //Actualizar el estado de pago de un pedido
    @PutMapping("/{id}/pagado")
    @Operation(summary = "Actualizar el estado de pago de un pedido", description = "Permite actualizar el estado de pago de un pedido específico utilizando su ID y el nuevo estado de pago proporcionado en el cuerpo de la solicitud.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de pago actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PedidoResponseDTO> actualizarPagado(@PathVariable Long id, @RequestBody boolean pedPagado) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPago(id, pedPagado));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Eliminar un pedido por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Permite eliminar un pedido específico utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}