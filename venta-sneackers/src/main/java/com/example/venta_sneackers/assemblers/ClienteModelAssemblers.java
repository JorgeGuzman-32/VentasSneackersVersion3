package com.example.venta_sneackers.assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.example.venta_sneackers.controllers.ClienteControllerV2;
import com.example.venta_sneackers.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssemblers implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteControllerV2.class).getCliente(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withRel("clientes"));
    }   

}
