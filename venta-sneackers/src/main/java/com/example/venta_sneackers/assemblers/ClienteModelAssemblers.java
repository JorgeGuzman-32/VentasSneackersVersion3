package com.example.venta_sneackers.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.venta_sneackers.controller.ClienteController;
import com.example.venta_sneackers.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssemblers implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @SuppressWarnings("null")
    @Override
    public @NonNull EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
            linkTo(methodOn(ClienteController.class).obtenerPorIdCliente(cliente.getIdCliente())).withSelfRel(),
            linkTo(methodOn(ClienteController.class).obtenerTodos()).withRel("clientes"));
    }
}
