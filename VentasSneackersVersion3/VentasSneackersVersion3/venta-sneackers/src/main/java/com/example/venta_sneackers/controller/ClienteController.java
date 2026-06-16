package com.example.venta_sneackers.controller;

import  com.example.venta_sneackers.model.Cliente;
import  com.example.venta_sneackers.Service.ClienteService;
import io.swagger.V3.oas.annotations.Operation;
import io.swagger.V3.oas.annotation.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.ClienteService;
import com.example.venta_sneackers.dto.ClienteRequestDTO;
import com.example.venta_sneackers.dto.ClienteResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/V1/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", descrption = "Operaciones relacionadas con los Clientes")
public class ClienteController {
    @Autowired
    private final ClienteService clienteService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping
     public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
        @Operation (summary ="Obtener todos loc lientes", description = "obtiene una lista de todos los clientes, ")
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
        @Schema(description = "Cliente")

        public class Cliente {
            @Schema(description = "Código del Cliente", example = 4567)
            private Long idCliente;
        }
    }

    @GetMapping("/buscar/{cliNombre}")
    public ResponseEntity<@Nullable Object> buscarPorNombre(@PathVariable String cliNombre) {
        return ResponseEntity.ok(clienteService.buscarPorNombre(cliNombre));
        
        public class Cliente { 
            @Schema(description = "Nombre del Cliente", example = "Pablo")
                private String cliNombre;}
    }


    @GetMapping("/buscar/direccion/{cliDireccion}")
    public ResponseEntity<@Nullable Object> buscarPorDireccion(@PathVariable String cliDireccion) {
        return ResponseEntity.ok(clienteService.buscarPorDireccion(cliDireccion));

           public class Cliente { 
            @Schema(description = "Direcccion del Cliente", example = "avenida siempre viva")
                private String cliDireccion;}
    }

    @GetMapping("/buscar/estado/{cliEstado}")
    public ResponseEntity<@Nullable Object> buscarPorEstado(@PathVariable String cliEstado) {
        return ResponseEntity.ok(clienteService.buscarPorEstado(cliEstado));
           public class Cliente { 
            @Schema(description = "Estado del Cliente", example = "En proceso")
                private String cliEstado;}
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.guardar(dto));

        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation =  Cliente.class)))
    }
    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));

            @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation =  Cliente.class)))

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();

        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation =  Cliente.class)))
    }

}
