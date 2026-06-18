package com.example.venta_sneackers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    ///////////////////////////                 GETs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //OBTENER TODOS LOS CLIENTES
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista de todos los clientes registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron clientes registrados en el sistema."
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
        List<ClienteResponseDTO> clientes = clienteService.obtenerTodos();
        if (clientes.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron clientes registrados en el sistema."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(clientes);
    }


    //OBTENER CLIENTE POR IDCLIENTE
    @GetMapping("/idCliente/{idCliente}")
    @Operation(summary = "Obtener cliente por ID Cliente", description = "Devuelve una lista de clientes que coinciden con el ID Cliente proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el ID Cliente proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron clientes con el ID Cliente proporcionado."
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
    public ResponseEntity<?> obtenerPorIdCliente(@PathVariable Long idCliente) {
        List<ClienteResponseDTO> clientes = clienteService.obtenerPorIdCliente(idCliente);
        if (clientes.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron clientes con el ID Cliente proporcionado."
            );
            return ResponseEntity.status(404).body(error);
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
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el nombre proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron clientes con el nombre proporcionado."
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
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String cliNombre) {
        List<ClienteResponseDTO> clientes = clienteService.buscarPorNombre(cliNombre);
        if (clientes.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron clientes con el nombre proporcionado."
            );
            return ResponseEntity.status(404).body(error);
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
        @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el estado proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron clientes con el estado proporcionado."
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
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String cliEstado) {
        List<ClienteResponseDTO> clientes = clienteService.buscarPorEstado(cliEstado);
        if (clientes.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron clientes con el estado proporcionado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(clientes);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                                ////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //CREAR NUEVO CLIENTE
    @PostMapping
    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente con la información proporcionada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Los datos proporcionados para crear el cliente no son válidos."
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
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO created = clienteService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //ACTUALIZAR CLIENTE EXISTENTE
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente existente", description = "Actualiza la información de un cliente existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Los datos de actualización proporcionados no son válidos."
                }
            """))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontró el cliente con el ID especificado."
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
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO updated = clienteService.actualizar(id, dto);
        if (updated == null) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontró el cliente con el ID especificado."
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(updated);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                               //////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //ELIMINAR CLIENTE POR ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente por ID", description = "Elimina un cliente existente con el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "El cliente que intenta eliminar no existe."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error interno al intentar eliminar el cliente."
                }
            """)))
    })
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
}