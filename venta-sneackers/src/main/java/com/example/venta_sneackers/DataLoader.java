package com.example.venta_sneackers;

import com.example.venta_sneackers.model.*;
import com.example.venta_sneackers.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ModeloRepository modeloRepository;
    private final TallaRepository tallaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) {

        if (modeloRepository.count() == 0) seedModelos();
        if (tallaRepository.count() == 0) seedTallas();
        if (productoRepository.count() == 0) seedProductos();
        if (clienteRepository.count() == 0) seedClientes();
        if (pedidoRepository.count() == 0) seedPedidos();

        log.info(">>> DataLoader: {} modelos, {} tallas, {} productos, {} clientes, {} pedidos.",
                modeloRepository.count(),
                tallaRepository.count(),
                productoRepository.count(),
                clienteRepository.count(),
                pedidoRepository.count());
    }

    private void seedModelos() {
        modeloRepository.save(new Modelo(null, "Zapatilla deportiva clasica", "Air Max", "verano", 2020, false));
        modeloRepository.save(new Modelo(null, "Icono del streetwear", "Superstar", "primavera", 2018, false));
        modeloRepository.save(new Modelo(null, "Edicion especial coleccionable", "Chuck Taylor", "invierno", 2023, true));
        modeloRepository.save(new Modelo(null, "Zapatilla de alto rendimiento", "Ultra Boost", "verano", 2021, false));
        modeloRepository.save(new Modelo(null, "Clasico del skate", "Old Skool", "otono", 2019, false));
        log.info(">>> DataLoader: modelos cargados.");
    }

    private void seedTallas() {
        tallaRepository.save(new Talla(null, "38", "EU"));
        tallaRepository.save(new Talla(null, "39", "EU"));
        tallaRepository.save(new Talla(null, "40", "EU"));
        tallaRepository.save(new Talla(null, "41", "EU"));
        tallaRepository.save(new Talla(null, "42", "EU"));
        log.info(">>> DataLoader: tallas cargadas.");
    }

    private void seedProductos() {
        Modelo airMax = modeloRepository.findAll().stream()
                .filter(m -> "Air Max".equals(m.getModNombre()))
                .findFirst().orElseThrow();

        Modelo superstar = modeloRepository.findAll().stream()
                .filter(m -> "Superstar".equals(m.getModNombre()))
                .findFirst().orElseThrow();

        Talla t40 = tallaRepository.findAll().stream()
                .filter(t -> "40".equals(t.getTallNombre()))
                .findFirst().orElseThrow();

        Talla t41 = tallaRepository.findAll().stream()
                .filter(t -> "41".equals(t.getTallNombre()))
                .findFirst().orElseThrow();

        productoRepository.save(new Producto(null, "Nike Air Max 90", "Clasico deportivo blanco",
                "2024-01-10", new BigDecimal("89.99"), 50, "Nike", "Blanco", "unisex", "adulto", t40, airMax));
        productoRepository.save(new Producto(null, "Nike Air Max 90 Negro", "Clasico deportivo negro",
                "2024-01-15", new BigDecimal("89.99"), 40, "Nike", "Negro", "masculino", "adulto", t41, airMax));
        productoRepository.save(new Producto(null, "Adidas Superstar Blanco", "Icono urbano blanco",
                "2024-02-01", new BigDecimal("75.00"), 30, "Adidas", "Blanco", "unisex", "adulto", t41, superstar));
        productoRepository.save(new Producto(null, "Adidas Superstar Negro", "Icono urbano negro",
                "2024-02-10", new BigDecimal("75.00"), 20, "Adidas", "Negro", "femenino", "adulto", t40, superstar));
        log.info(">>> DataLoader: productos cargados.");
    }

    private void seedClientes() {
        clienteRepository.save(new Cliente(null, "Juan Perez", "Calle siempre viva 1", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Maria Gomez", "Avenida central 123", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Carlos Sanchez", "Calle falsa 456", "Inhabilitado"));
        clienteRepository.save(new Cliente(null, "Ana Rodriguez", "Avenida del sol 789", "Habilitado"));
        log.info(">>> DataLoader: clientes cargados.");
    }

    private void seedPedidos() {
        java.util.List<Cliente> clientes = clienteRepository.findAll();
        java.util.List<Producto> productos = productoRepository.findAll();

        Producto producto1 = productos.stream()
                .filter(p -> "Nike Air Max 90".equals(p.getProNombre()))
                .findFirst().orElseThrow();
        Producto producto2 = productos.stream()
                .filter(p -> "Nike Air Max 90 Negro".equals(p.getProNombre()))
                .findFirst().orElseThrow();
        Producto producto3 = productos.stream()
                .filter(p -> "Adidas Superstar Blanco".equals(p.getProNombre()))
                .findFirst().orElseThrow();
        Producto producto4 = productos.stream()
                .filter(p -> "Adidas Superstar Negro".equals(p.getProNombre()))
                .findFirst().orElseThrow();

        Pedido pedido1 = new Pedido();
        pedido1.setPedFechaCompra("2024-01-15");
        pedido1.setPedPagado(true);
        pedido1.setCliente(clientes.get(0));
        DetallePedido detalle1 = new DetallePedido();
        detalle1.setProducto(producto1);
        detalle1.setCantidad(1);
        detalle1.setPrecioUnitario(producto1.getProPrecio());
        detalle1.setSubtotal(producto1.getProPrecio());
        pedido1.addDetalle(detalle1);
        pedido1.setPedSubtotal(detalle1.getSubtotal());
        pedido1.setPedDescuento(new BigDecimal("5.00"));
        pedido1.setPedTotal(detalle1.getSubtotal().subtract(new BigDecimal("5.00")));
        pedidoRepository.save(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setPedFechaCompra("2024-02-20");
        pedido2.setPedPagado(false);
        pedido2.setCliente(clientes.get(1));
        DetallePedido detalle2a = new DetallePedido();
        detalle2a.setProducto(producto2);
        detalle2a.setCantidad(1);
        detalle2a.setPrecioUnitario(producto2.getProPrecio());
        detalle2a.setSubtotal(producto2.getProPrecio());
        pedido2.addDetalle(detalle2a);
        DetallePedido detalle2b = new DetallePedido();
        detalle2b.setProducto(producto3);
        detalle2b.setCantidad(1);
        detalle2b.setPrecioUnitario(producto3.getProPrecio());
        detalle2b.setSubtotal(producto3.getProPrecio());
        pedido2.addDetalle(detalle2b);
        pedido2.setPedSubtotal(detalle2a.getSubtotal().add(detalle2b.getSubtotal()));
        pedido2.setPedDescuento(new BigDecimal("10.00"));
        pedido2.setPedTotal(pedido2.getPedSubtotal().subtract(new BigDecimal("10.00")));
        pedidoRepository.save(pedido2);

        Pedido pedido3 = new Pedido();
        pedido3.setPedFechaCompra("2024-03-10");
        pedido3.setPedPagado(true);
        pedido3.setCliente(clientes.get(2));
        DetallePedido detalle3 = new DetallePedido();
        detalle3.setProducto(producto4);
        detalle3.setCantidad(1);
        detalle3.setPrecioUnitario(producto4.getProPrecio());
        detalle3.setSubtotal(producto4.getProPrecio());
        pedido3.addDetalle(detalle3);
        pedido3.setPedSubtotal(detalle3.getSubtotal());
        pedido3.setPedDescuento(BigDecimal.ZERO);
        pedido3.setPedTotal(detalle3.getSubtotal());
        pedidoRepository.save(pedido3);

        log.info(">>> DataLoader: pedidos cargados.");
    }
}
