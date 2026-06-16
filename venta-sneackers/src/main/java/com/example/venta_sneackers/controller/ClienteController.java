package com.example.venta_sneackers.controller;

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

public class ClienteController {
    private final ClienteService clienteService;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping
     public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @GetMapping("/buscar/{cliNombre}")
    public ResponseEntity<@Nullable Object> buscarPorNombre(@PathVariable String cliNombre) {
        return ResponseEntity.ok(clienteService.buscarPorNombre(cliNombre));
    }

    @GetMapping("/buscar/direccion/{cliDireccion}")
    public ResponseEntity<@Nullable Object> buscarPorDireccion(@PathVariable String cliDireccion) {
        return ResponseEntity.ok(clienteService.buscarPorDireccion(cliDireccion));
    }

    @GetMapping("/buscar/estado/{cliEstado}")
    public ResponseEntity<@Nullable Object> buscarPorEstado(@PathVariable String cliEstado) {
        return ResponseEntity.ok(clienteService.buscarPorEstado(cliEstado));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.guardar(dto));
    }
    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
