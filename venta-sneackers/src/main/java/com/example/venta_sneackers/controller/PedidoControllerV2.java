package com.example.venta_sneackers.controller;

import com.example.venta_sneackers.assemblers.PedidoModelAssemblers;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssemblers pedidoModelAssemblers;
    
    // Metodo Personalizado para obtener pedidos por fecha
    @GetMapping(value = "/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosByFecha(@PathVariable String fecha) {
        Date date = new Date(); // Convertir String a Date según el formato
        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosByFecha(date).stream()
                .map(pedidoModelAssemblers::toModel)
                .collect(Collectors.toList());  
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByFecha(fecha)).withSelfRel());
    }   

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAllPedidos() {
        List<EntityModel<Pedido>> pedidos = pedidoService.getAllPedidos().stream()
                .map(pedidoModelAssemblers::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withSelfRel());
    }

    //Metodo Personalizado para obtener pedidos por cliente
    @GetMapping(value = "/cliente/{clienteId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosByCliente(@PathVariable Long clienteId) {
        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosBy  Cliente(clienteId).stream()
                .map(pedidoModelAssemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByCliente(clienteId)).  withSelfRel());     
    }

    //Metodo Personalizado para obtener pedidos por estado
    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosByEstado(@PathVariable String estado) {
        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosByEstado(estado).stream()
                .map(pedidoModelAssemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByEstado(estado)).withSelfRel());     
    }   

    //Metodo Pesonalizado Para obtener pedidos entre dos fechas
    @GetMapping(value = "/fecha/{start}/{end}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosBetweenFechas(@PathVariable String start, @PathVariable String end) {
        Date startDate = new Date(); // Convertir String a Date según el formato
        Date endDate = new Date(); // Convertir String a Date según el formato
        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosBetweenFechas(startDate, endDate).stream()
                .map(pedidoModelAssemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosBetweenFechas(start, end)).withSelfRel());     
    }   

    //Metodo Personalizado para obtener pedidos por cliente y estado
    @GetMapping(value = "/cliente/{clienteId}/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosByClienteAndEstado(@PathVariable Long clienteId, @PathVariable String estado) {
        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosByClienteAndEstado(clienteId, estado).stream()
                .map(pedidoModelAssemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByClienteAndEstado(clienteId, estado)).withSelfRel());     
    }   

    //Metodo Personalizado para obtener el total de pedidos por cliente
    @GetMapping(value = "/cliente/{clienteId}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Double> getTotalPedidosByCliente(@PathVariable Long clienteId) {
        Double total = pedidoService.getTotalPedidosByCliente(clienteId);
        return ResponseEntity.ok(total);
    }
    
}
