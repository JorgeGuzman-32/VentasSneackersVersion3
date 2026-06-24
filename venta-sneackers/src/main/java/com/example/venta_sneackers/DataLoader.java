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

        pedidoRepository.save(new Pedido(null, "2024-01-15", new BigDecimal("89.99"),
                new BigDecimal("5.00"), new BigDecimal("84.99"), true, clientes.get(0)));
        pedidoRepository.save(new Pedido(null, "2024-02-20", new BigDecimal("150.00"),
                new BigDecimal("10.00"), new BigDecimal("140.00"), false, clientes.get(1)));
        pedidoRepository.save(new Pedido(null, "2024-03-10", new BigDecimal("75.00"),
                new BigDecimal("0.00"), new BigDecimal("75.00"), true, clientes.get(2)));
        log.info(">>> DataLoader: pedidos cargados.");
    }
}
