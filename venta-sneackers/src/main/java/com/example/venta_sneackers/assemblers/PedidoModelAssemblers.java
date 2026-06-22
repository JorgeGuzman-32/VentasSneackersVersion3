package com.example.venta_sneackers.assemblers;

import com.example.venta_sneackers.controllers.PedidoControllerV2;
import com.example.venta_sneackers.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Component
public class PedidoModelAssemblers implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).getPedido(pedido.getId())).withSelfRel();
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withRel("pedidos"));
    }

}
