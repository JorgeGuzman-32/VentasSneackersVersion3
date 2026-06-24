package com.example.venta_sneackers.controller;

import com.example.venta_sneackers.assemblers.ClienteModelAssemblers;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssemblers clienteModelAssemblers;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> getAllClientes() {
        List<EntityModel<Cliente>> clientes = clienteService.getAllClientes().stream()
                .map(clienteModelAssemblers::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> getCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(clienteModelAssemblers.toModel(cliente));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> createCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteService.createCliente(cliente);
        return ResponseEntity.ok(clienteModelAssemblers.toModel(newCliente));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente updatedCliente = clienteService.updateCliente(id, cliente);
        return ResponseEntity.ok(clienteModelAssemblers.toModel(updatedCliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}  
