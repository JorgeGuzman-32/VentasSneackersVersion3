package com.example.venta_sneackers.Config;

import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.model.Detalle;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.ClienteRepository;
import com.example.venta_sneackers.repository.DetalleRepository;
import com.example.venta_sneackers.repository.ModeloRepository;
import com.example.venta_sneackers.repository.PedidoRepository;
import com.example.venta_sneackers.repository.ProductoRepository;
import com.example.venta_sneackers.repository.TallaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ModeloRepository modeloRepository;
    private final TallaRepository tallaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final DetalleRepository detalleRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (modeloRepository.count() == 0) {
            seedModelos();
        }

        if (tallaRepository.count() == 0) {
            seedTallas();
        }

        if (productoRepository.count() == 0) {
            seedProductos();
        }

        if (clienteRepository.count() == 0) {
            seedClientes();
        }

        if (pedidoRepository.count() == 0) {
            seedPedidos();
        }

        log.info(
                ">>> DataInitializer: {} modelos, {} tallas, {} productos, {} clientes, {} detalles y {} pedidos.",
                modeloRepository.count(),
                tallaRepository.count(),
                productoRepository.count(),
                clienteRepository.count(),
                detalleRepository.count(),
                pedidoRepository.count()
        );
    }

    private void seedModelos() {
        Modelo airMax = modeloRepository.save(new Modelo(null, "Zapatilla deportiva clasica", "Air Max", "verano", 2020, false));
        Modelo superstar = modeloRepository.save(new Modelo(null, "Icono del streetwear", "Superstar", "primavera", 2018, false));
        Modelo chuckTaylor = modeloRepository.save(new Modelo(null, "Edicion especial coleccionable", "Chuck Taylor", "invierno", 2023, true));
        Modelo ultraBoost = modeloRepository.save(new Modelo(null, "Zapatilla de alto rendimiento", "Ultra Boost", "verano", 2021, false));
        Modelo oldSkool = modeloRepository.save(new Modelo(null, "Clasico del skate", "Old Skool", "otono", 2019, false));
        Modelo blazer = modeloRepository.save(new Modelo(null, "Estilo retro basketball", "Blazer Mid", "primavera", 2022, false));
        Modelo yeezy = modeloRepository.save(new Modelo(null, "Colaboracion exclusiva", "Yeezy 350", "invierno", 2022, true));
        Modelo airForce = modeloRepository.save(new Modelo(null, "Icono del basketball urbano", "Air Force 1", "verano", 2017, false));

        log.info(">>> DataInitializer: modelos base cargados. AirMax={}, Superstar={}, ChuckTaylor={}, UltraBoost={}, OldSkool={}, Blazer={}, Yeezy={}, AirForce={}",
                airMax.getIdModelo(), superstar.getIdModelo(), chuckTaylor.getIdModelo(), ultraBoost.getIdModelo(), oldSkool.getIdModelo(), blazer.getIdModelo(), yeezy.getIdModelo(), airForce.getIdModelo());
    }

    private void seedTallas() {
        Talla eu36 = tallaRepository.save(new Talla(null, "36", "EU"));
        Talla eu37 = tallaRepository.save(new Talla(null, "37", "EU"));
        Talla eu38 = tallaRepository.save(new Talla(null, "38", "EU"));
        Talla eu39 = tallaRepository.save(new Talla(null, "39", "EU"));
        Talla eu40 = tallaRepository.save(new Talla(null, "40", "EU"));
        Talla eu41 = tallaRepository.save(new Talla(null, "41", "EU"));
        Talla eu42 = tallaRepository.save(new Talla(null, "42", "EU"));
        Talla eu43 = tallaRepository.save(new Talla(null, "43", "EU"));

        log.info(">>> DataInitializer: tallas base cargadas. 36={}, 37={}, 38={}, 39={}, 40={}, 41={}, 42={}, 43={}",
                eu36.getId(), eu37.getId(), eu38.getId(), eu39.getId(), eu40.getId(), eu41.getId(), eu42.getId(), eu43.getId());
    }

    private void seedProductos() {
        Modelo airMax = modeloRepository.findAll().stream()
                .filter(m -> "Air Max".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Air Max"));
        Modelo superstar = modeloRepository.findAll().stream()
                .filter(m -> "Superstar".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Superstar"));
        Modelo chuckTaylor = modeloRepository.findAll().stream()
                .filter(m -> "Chuck Taylor".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Chuck Taylor"));
        Modelo ultraBoost = modeloRepository.findAll().stream()
                .filter(m -> "Ultra Boost".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Ultra Boost"));
        Modelo oldSkool = modeloRepository.findAll().stream()
                .filter(m -> "Old Skool".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Old Skool"));
        Modelo blazer = modeloRepository.findAll().stream()
                .filter(m -> "Blazer Mid".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Blazer Mid"));
        Modelo yeezy = modeloRepository.findAll().stream()
                .filter(m -> "Yeezy 350".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Yeezy 350"));
        Modelo airForce = modeloRepository.findAll().stream()
                .filter(m -> "Air Force 1".equals(m.getModNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro el modelo Air Force 1"));

        Talla eu36 = tallaRepository.findAll().stream()
                .filter(t -> "36".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 36"));
        Talla eu37 = tallaRepository.findAll().stream()
                .filter(t -> "37".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 37"));
        Talla eu38 = tallaRepository.findAll().stream()
                .filter(t -> "38".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 38"));
        Talla eu39 = tallaRepository.findAll().stream()
                .filter(t -> "39".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 39"));
        Talla eu40 = tallaRepository.findAll().stream()
                .filter(t -> "40".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 40"));
        Talla eu41 = tallaRepository.findAll().stream()
                .filter(t -> "41".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 41"));
        Talla eu42 = tallaRepository.findAll().stream()
                .filter(t -> "42".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 42"));
        Talla eu43 = tallaRepository.findAll().stream()
                .filter(t -> "43".equals(t.getTallNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontro la talla 43"));

        productoRepository.save(new Producto(null, "Nike Air Max 90", "Clasico deportivo blanco", "2024-01-10", new BigDecimal("89.99"), 50, "Nike", "Blanco", "unisex", "adulto", eu40, airMax));
        productoRepository.save(new Producto(null, "Nike Air Max 90 Negro", "Clasico deportivo negro", "2024-01-15", new BigDecimal("89.99"), 40, "Nike", "Negro", "masculino", "adulto", eu42, airMax));
        productoRepository.save(new Producto(null, "Nike Air Max 90 Kids", "Clasico deportivo para ninos", "2024-01-20", new BigDecimal("65.99"), 25, "Nike", "Azul", "unisex", "nino", eu36, airMax));
        productoRepository.save(new Producto(null, "Adidas Superstar Blanco", "Icono urbano blanco", "2024-02-01", new BigDecimal("75.00"), 30, "Adidas", "Blanco", "unisex", "adulto", eu41, superstar));
        productoRepository.save(new Producto(null, "Adidas Superstar Negro", "Icono urbano negro", "2024-02-10", new BigDecimal("75.00"), 20, "Adidas", "Negro", "femenino", "adulto", eu38, superstar));
        productoRepository.save(new Producto(null, "Converse Chuck Taylor Rojo", "Edicion limitada roja", "2024-03-01", new BigDecimal("120.00"), 10, "Converse", "Rojo", "femenino", "adulto", eu37, chuckTaylor));
        productoRepository.save(new Producto(null, "Converse Chuck Taylor Kids", "Edicion limitada para ninos", "2024-03-05", new BigDecimal("85.00"), 15, "Converse", "Verde", "unisex", "nino", eu36, chuckTaylor));
        productoRepository.save(new Producto(null, "Adidas Ultra Boost 22", "Alto rendimiento running", "2024-04-01", new BigDecimal("150.00"), 35, "Adidas", "Gris", "masculino", "adulto", eu43, ultraBoost));
        productoRepository.save(new Producto(null, "Adidas Ultra Boost Femenino", "Alto rendimiento mujer", "2024-04-10", new BigDecimal("145.00"), 20, "Adidas", "Rosa", "femenino", "adulto", eu38, ultraBoost));
        productoRepository.save(new Producto(null, "Vans Old Skool Negro", "Clasico del skate negro", "2024-05-01", new BigDecimal("65.00"), 45, "Vans", "Negro", "unisex", "adulto", eu41, oldSkool));
        productoRepository.save(new Producto(null, "Vans Old Skool Kids", "Clasico del skate para ninos", "2024-05-10", new BigDecimal("50.00"), 30, "Vans", "Rojo", "unisex", "nino", eu36, oldSkool));
        productoRepository.save(new Producto(null, "Nike Blazer Mid Blanco", "Retro basketball blanco", "2024-06-01", new BigDecimal("95.00"), 25, "Nike", "Blanco", "masculino", "adulto", eu42, blazer));
        productoRepository.save(new Producto(null, "Nike Blazer Mid Mujer", "Retro basketball mujer", "2024-06-15", new BigDecimal("90.00"), 20, "Nike", "Beige", "femenino", "adulto", eu39, blazer));
        productoRepository.save(new Producto(null, "Yeezy 350 Zebra", "Edicion limitada zebra", "2024-07-01", new BigDecimal("250.00"), 5, "Adidas", "Blanco", "unisex", "adulto", eu41, yeezy));
        productoRepository.save(new Producto(null, "Yeezy 350 Bred", "Edicion limitada bred", "2024-07-15", new BigDecimal("280.00"), 3, "Adidas", "Negro", "unisex", "adulto", eu43, yeezy));
        productoRepository.save(new Producto(null, "Nike Air Force 1 Blanco", "Icono basketball blanco", "2024-08-01", new BigDecimal("85.00"), 60, "Nike", "Blanco", "unisex", "adulto", eu40, airForce));
        productoRepository.save(new Producto(null, "Nike Air Force 1 Kids", "Icono basketball ninos", "2024-08-10", new BigDecimal("60.00"), 40, "Nike", "Blanco", "unisex", "nino", eu36, airForce));

        log.info(">>> DataInitializer: productos base cargados.");
    }

    private void seedClientes() {
        clienteRepository.save(new Cliente(null, "Juan Perez", "Calle siempre viva 1", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Maria Gomez", "Avenida central 123", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Carlos Sanchez", "Calle falsa 456", "Inhabilitado"));
        clienteRepository.save(new Cliente(null, "Ana Rodriguez", "Avenida del sol 789", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Luis Fernandez", "Calle del viento 321", "Inhabilitado"));
        clienteRepository.save(new Cliente(null, "Sofia Martinez", "Avenida de la luna 654", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Diego Ramirez", "Calle del mar 987", "Habilitado"));
        clienteRepository.save(new Cliente(null, "Laura Torres", "Avenida de las estrellas 159", "Inhabilitado"));
        clienteRepository.save(new Cliente(null, "Jorge Diaz", "Calle del bosque 753", "Habilitado"));

        log.info(">>> DataInitializer: clientes base cargados.");
    }

    private void seedPedidos() {
        if (clienteRepository.count() == 0) {
            throw new IllegalStateException("No hay clientes disponibles para crear pedidos.");
        }

        java.util.List<Cliente> clientes = clienteRepository.findAll().stream()
                .sorted(java.util.Comparator.comparing(Cliente::getIdCliente))
                .toList();

        if (clientes.size() < 6) {
            throw new IllegalStateException("Se necesitan al menos 6 clientes para crear pedidos.");
        }

        Cliente cliente1 = clientes.get(0);
        Cliente cliente2 = clientes.get(1);
        Cliente cliente3 = clientes.get(2);
        Cliente cliente4 = clientes.get(3);
        Cliente cliente5 = clientes.get(4);
        Cliente cliente6 = clientes.get(5);

        Detalle detalle1 = detalleRepository.save(new Detalle(null));
        Detalle detalle2 = detalleRepository.save(new Detalle(null));
        Detalle detalle3 = detalleRepository.save(new Detalle(null));
        Detalle detalle4 = detalleRepository.save(new Detalle(null));
        Detalle detalle5 = detalleRepository.save(new Detalle(null));
        Detalle detalle6 = detalleRepository.save(new Detalle(null));

        pedidoRepository.save(new Pedido(null, "2024-01-15", new BigDecimal("89.00"), new BigDecimal("5.00"), new BigDecimal("84.00"), detalle1, cliente1));
        pedidoRepository.save(new Pedido(null, "2024-02-20", new BigDecimal("150.00"), new BigDecimal("10.00"), new BigDecimal("140.00"), detalle2, cliente2));
        pedidoRepository.save(new Pedido(null, "2024-03-10", new BigDecimal("120.00"), new BigDecimal("0.00"), new BigDecimal("120.00"), detalle3, cliente3));
        pedidoRepository.save(new Pedido(null, "2024-04-05", new BigDecimal("200.00"), new BigDecimal("15.00"), new BigDecimal("185.00"), detalle4, cliente4));
        pedidoRepository.save(new Pedido(null, "2024-05-12", new BigDecimal("95.00"), new BigDecimal("8.00"), new BigDecimal("87.00"), detalle5, cliente5));
        pedidoRepository.save(new Pedido(null, "2024-06-18", new BigDecimal("175.00"), new BigDecimal("12.00"), new BigDecimal("163.00"), detalle6, cliente6));

        log.info(">>> DataInitializer: pedidos base cargados.");
    }
}