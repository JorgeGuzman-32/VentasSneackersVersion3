package com.example.venta_sneackers.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.venta_sneackers.controller.PedidoController;
import com.example.venta_sneackers.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssemblers implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @SuppressWarnings("null")
    @Override
    public @NonNull EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
            linkTo(methodOn(PedidoController.class).obtenerPorId(pedido.getIdPedido())).withSelfRel(),
            linkTo(methodOn(PedidoController.class).obtenerTodos()).withRel("pedidos"));    

        }
    }