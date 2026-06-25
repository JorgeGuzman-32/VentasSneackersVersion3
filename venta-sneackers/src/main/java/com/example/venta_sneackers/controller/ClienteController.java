package com.example.venta_sneackers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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

import com.example.venta_sneackers.Service.ClienteService;
import com.example.venta_sneackers.dto.ClienteRequestDTO;
import com.example.venta_sneackers.dto.ClienteResponseDTO;

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

@RestController
@RequestMapping("/api/V1/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones relacionadas con los Clientes")
@SuppressWarnings("null")
public class ClienteController {

    private final ClienteService clienteService;


    ////////////////////////////////////////////////////////////////////
    ///////////////////////////   GETS    //////////////////////////////
    ////////////////////////////////////////////////////////////////////
    
    
    //GET - Obtener todos los clientes
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista de todos los clientes registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = ClienteResponseDTO.class),
                                examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "cliNombre": "Juan Perez",
                                      "cliApellido": "Gomez",
                                      "cliDireccion": "Calle Falsa 123",
                                      "cliTelefono": "555-1234",
                                      "cliEmail": "juan.perez@example.com"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes registrados en el sistema",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontraron clientes registrados en el sistema."
                                    }
                                    """)))
    })
    public ResponseEntity<?> obtenerTodos() {
        List<ClienteResponseDTO> clientes = clienteService.obtenerTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "No encontrado",
                    "descripcion", "No se encontraron clientes registrados en el sistema."
            ));
        }

        List<EntityModel<ClienteResponseDTO>> resources = clientes.stream()
                .map(this::toClientModel)
                .toList();

        CollectionModel<EntityModel<ClienteResponseDTO>> collection = CollectionModel.of(
                resources,
                linkTo(methodOn(ClienteController.class).obtenerTodos()).withSelfRel()
        );

        collection.add(
                linkTo(methodOn(ClienteController.class).obtenerPorIdCliente(null)).withRel("buscarPorIdCliente"),
                linkTo(methodOn(ClienteController.class).obtenerPorNombre(null)).withRel("buscarPorNombre"),
                linkTo(methodOn(ClienteController.class).obtenerPorEstado(null)).withRel("buscarPorEstado")
        );

        return ResponseEntity.ok(collection);
    }


    //GET - Obtener cliente por ID
    @GetMapping(value = "/idCliente/{idCliente}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener cliente por ID Cliente", description = "Devuelve una lista de clientes que coinciden con el ID Cliente proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = ClienteResponseDTO.class),
                    examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "cliNombre": "Juan Perez",
                                      "cliDireccion": "Calle Falsa 123",
                                      "cliTelefono": "555-1234",
                                      "cliEstado": "Activo"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el ID Cliente proporcionado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontraron clientes con el ID Cliente proporcionado."
                                    }
                                    """)))
    })
    public ResponseEntity<?> obtenerPorIdCliente(@PathVariable Long idCliente) {
        List<ClienteResponseDTO> clientes = clienteService.obtenerPorIdCliente(idCliente);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "No encontrado",
                    "descripcion", "No se encontraron clientes con el ID Cliente proporcionado."
            ));
        }

        List<EntityModel<ClienteResponseDTO>> resources = clientes.stream()
                .map(this::toClientModel)
                .toList();

        CollectionModel<EntityModel<ClienteResponseDTO>> collection = CollectionModel.of(
                resources,
                linkTo(methodOn(ClienteController.class).obtenerPorIdCliente(idCliente)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    //GET - Obtener cliente por nombre
    @GetMapping(value = "/cliNombre/{cliNombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener cliente por nombre", description = "Devuelve una lista de clientes que coinciden con el nombre proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = ClienteResponseDTO.class),
                    examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "cliNombre": "Juan Perez",
                                      "cliDireccion": "Calle Falsa 123",
                                      "cliTelefono": "555-1234",
                                      "cliEstado": "Activo"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el nombre proporcionado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontraron clientes con el nombre proporcionado."
                                    }
                                    """)))
    })

    //GET - Obtener cliente por nombre
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String cliNombre) {
        List<ClienteResponseDTO> clientes = clienteService.buscarPorNombre(cliNombre);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "No encontrado",
                    "descripcion", "No se encontraron clientes con el nombre proporcionado."
            ));
        }

        List<EntityModel<ClienteResponseDTO>> resources = clientes.stream()
                .map(this::toClientModel)
                .toList();

        CollectionModel<EntityModel<ClienteResponseDTO>> collection = CollectionModel.of(
                resources,
                linkTo(methodOn(ClienteController.class).obtenerPorNombre(cliNombre)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    //GET - Obtener cliente por estado
    @GetMapping(value = "/cliEstado/{cliEstado}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener cliente por estado", description = "Devuelve una lista de clientes que coinciden con el estado proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente",
                    content = @Content(mediaType = "application/hal+json",
                            schema = @Schema(implementation = ClienteResponseDTO.class),
                    examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "cliNombre": "Juan Perez",
                                      "cliDireccion": "Calle Falsa 123",
                                      "cliTelefono": "555-1234",
                                      "cliEstado": "Activo"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes con el estado proporcionado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontraron clientes con el estado proporcionado."
                                    }
                                    """)))
    })
    public ResponseEntity<?> obtenerPorEstado(@PathVariable String cliEstado) {
        List<ClienteResponseDTO> clientes = clienteService.buscarPorEstado(cliEstado);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "No encontrado",
                    "descripcion", "No se encontraron clientes con el estado proporcionado."
            ));
        }

        List<EntityModel<ClienteResponseDTO>> resources = clientes.stream()
                .map(this::toClientModel)
                .toList();

        CollectionModel<EntityModel<ClienteResponseDTO>> collection = CollectionModel.of(
                resources,
                linkTo(methodOn(ClienteController.class).obtenerPorEstado(cliEstado)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    private EntityModel<ClienteResponseDTO> toClientModel(ClienteResponseDTO cliente) {
        Long id = cliente.getId();
        return EntityModel.of(
                cliente,
                linkTo(methodOn(ClienteController.class).obtenerPorIdCliente(id)).withSelfRel()
        );
    }

    ///////////////////////////////////////////////////////////////////
    ////////////////////////////   POST  //////////////////////////////
    ///////////////////////////////////////////////////////////////////
    
    //POST - Crear un nuevo cliente
    @PostMapping
    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente con la información proporcionada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponseDTO.class),
                        examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "cliNombre": "Juan Perez",
                                      "cliDireccion": "Calle Falsa 123",
                                      "cliTelefono": "555-1234",
                                      "cliEstado": "Activo"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "Solicitud inválida",
                                      "descripcion": "La información proporcionada para crear el cliente es inválida."
                                    }
                                    """)))
    })
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO created = clienteService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////   PUTs   //////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    
    //PUT - Actualizar un cliente existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente existente", description = "Actualiza la información de un cliente existente con el ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponseDTO.class),
                        examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "cliNombre": "Juan Perez",
                                      "cliDireccion": "Calle Falsa 123",
                                      "cliTelefono": "555-1234",
                                      "cliEstado": "Activo"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontró el cliente con el ID especificado."
                                    }
                                    """)))
    })
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO updated = clienteService.actualizar(id, dto);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "No encontrado",
                    "descripcion", "No se encontró el cliente con el ID especificado."
            ));
        }
        return ResponseEntity.ok(updated);
    }

    /////////////////////////////////////////////////////////////////////
    //////////////////////////////////DELETE/////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    //DELETE - Eliminar un cliente existente 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente por ID", description = "Elimina un cliente existente con el ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "mensaje": "Cliente eliminado exitosamente."
                                    }
                                    """))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "error": "No encontrado",
                                      "descripcion": "No se encontró el cliente con el ID especificado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
