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

import com.example.venta_sneackers.Service.ClienteService;
import com.example.venta_sneackers.dto.ClienteRequestDTO;
import com.example.venta_sneackers.dto.ClienteResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/V1/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones relacionadas con los Clientes")
public class ClienteController {
    
    private final ClienteService clienteService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //OBTENER TODOS LOS CLIENTES
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista de todos los clientes registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }


    //OBTENER CLIENTE POR IDCLIENTE
    @GetMapping("/idCliente/{idCliente}")
    @Operation(summary = "Obtener cliente por ID Cliente", description = "Devuelve una lista de clientes que coinciden con el ID Cliente proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el ID Cliente proporcionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ClienteResponseDTO>> obtenerPorIdCliente(@PathVariable Long idCliente) {
        List<ClienteResponseDTO> clientes = clienteService.obtenerPorIdCliente(idCliente);
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    //OBTENER CLIENTE POR NOMBRE
    @GetMapping("/cliNombre/{cliNombre}")
    @Operation(summary = "Obtener cliente por nombre", description = "Devuelve una lista de clientes que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el nombre proporcionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ClienteResponseDTO>> obtenerPorNombre(@PathVariable String cliNombre) {
        List<ClienteResponseDTO> clientes = clienteService.buscarPorNombre(cliNombre);
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    //OBTENER CLIENTE POR ESTADO
    @GetMapping("/cliEstado/{cliEstado}")
    @Operation(summary = "Obtener cliente por estado", description = "Devuelve una lista de clientes que coinciden con el estado proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el estado proporcionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    public ResponseEntity<List<ClienteResponseDTO>> obtenerPorEstado(@PathVariable String cliEstado) {
        List<ClienteResponseDTO> clientes = clienteService.buscarPorEstado(cliEstado);
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //CREAR NUEVO CLIENTE
    @PostMapping
    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente con la información proporcionada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

        public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO dto) {
            ClienteResponseDTO created = clienteService.guardar(dto);
            return ResponseEntity.status(201).body(created);
        }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //ACTUALIZAR CLIENTE EXISTENTE
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente existente", description = "Actualiza la información de un cliente existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO updated = clienteService.actualizar(id, dto);
        return ResponseEntity.ok(updated);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //ELIMINAR CLIENTE POR ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente por ID", description = "Elimina un cliente existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    

}