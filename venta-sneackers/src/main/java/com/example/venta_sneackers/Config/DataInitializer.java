package com.example.venta_sneackers.Config;

import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.model.DetallePedido;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.ClienteRepository;
import com.example.venta_sneackers.repository.DetallePedidoRepository;
import com.example.venta_sneackers.repository.ModeloRepository;
import com.example.venta_sneackers.repository.PedidoRepository;
import com.example.venta_sneackers.repository.ProductoRepository;
import com.example.venta_sneackers.repository.TallaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ModeloRepository modeloRepository;
    private final TallaRepository tallaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
        private final DetallePedidoRepository detallePedidoRepository;

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

                if (detallePedidoRepository.count() == 0 && pedidoRepository.count() > 0 && productoRepository.count() > 0) {
                        seedDetallesDesdeEntidadesRelacionadas();
                }

        log.info(
                                ">>> DataInitializer: {} modelos, {} tallas, {} productos, {} clientes, {} pedidos y {} detalles.",
                modeloRepository.count(),
                tallaRepository.count(),
                productoRepository.count(),
                clienteRepository.count(),
                                pedidoRepository.count(),
                                detallePedidoRepository.count()
        );
    }

        private void seedDetallesDesdeEntidadesRelacionadas() {
                java.util.List<Pedido> pedidos = pedidoRepository.findAll().stream()
                                .sorted(java.util.Comparator.comparing(Pedido::getIdPedido))
                                .toList();

                java.util.List<Producto> productos = productoRepository.findAll().stream()
                                .sorted(java.util.Comparator.comparing(Producto::getIdProducto))
                                .toList();

                if (pedidos.isEmpty() || productos.isEmpty()) {
                        return;
                }

                for (int i = 0; i < pedidos.size(); i++) {
                        Pedido pedido = pedidos.get(i);

                        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
                                continue;
                        }

                        Producto productoA = productos.get(i % productos.size());
                        DetallePedido detalleA = new DetallePedido();
                        detalleA.setProducto(productoA);
                        detalleA.setCantidad(1);
                        detalleA.setPrecioUnitario(productoA.getProPrecio());
                        detalleA.setSubtotal(productoA.getProPrecio());
                        pedido.addDetalle(detalleA);

                        if (i % 2 == 0 && productos.size() > 1) {
                                Producto productoB = productos.get((i + 1) % productos.size());
                                DetallePedido detalleB = new DetallePedido();
                                detalleB.setProducto(productoB);
                                detalleB.setCantidad(1);
                                detalleB.setPrecioUnitario(productoB.getProPrecio());
                                detalleB.setSubtotal(productoB.getProPrecio());
                                pedido.addDetalle(detalleB);
                        }

                        BigDecimal subtotal = pedido.getDetalles().stream()
                                        .map(DetallePedido::getSubtotal)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                        BigDecimal descuento = pedido.getPedDescuento() != null ? pedido.getPedDescuento() : BigDecimal.ZERO;
                        pedido.setPedSubtotal(subtotal);
                        pedido.setPedDescuento(descuento);
                        pedido.setPedTotal(subtotal.subtract(descuento));
                }

                pedidoRepository.saveAll(pedidos);
                log.info(">>> DataInitializer: detalle_pedidos creados desde pedidos/productos existentes.");
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

        java.util.List<Producto> productos = productoRepository.findAll();
        Producto producto1 = productos.stream().filter(p -> "Nike Air Max 90".equals(p.getProNombre())).findFirst().orElseThrow();
        Producto producto2 = productos.stream().filter(p -> "Nike Air Max 90 Negro".equals(p.getProNombre())).findFirst().orElseThrow();
        Producto producto3 = productos.stream().filter(p -> "Adidas Superstar Blanco".equals(p.getProNombre())).findFirst().orElseThrow();
        Producto producto4 = productos.stream().filter(p -> "Adidas Superstar Negro".equals(p.getProNombre())).findFirst().orElseThrow();
        Producto producto5 = productos.stream().filter(p -> "Converse Chuck Taylor Rojo".equals(p.getProNombre())).findFirst().orElseThrow();
        Producto producto6 = productos.stream().filter(p -> "Adidas Ultra Boost 22".equals(p.getProNombre())).findFirst().orElseThrow();

        Pedido pedido1 = new Pedido();
        pedido1.setPedFechaCompra("2024-01-15");
        pedido1.setPedPagado(true);
        pedido1.setCliente(cliente1);
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
        pedido2.setCliente(cliente2);
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
        pedido3.setCliente(cliente3);
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

        Pedido pedido4 = new Pedido();
        pedido4.setPedFechaCompra("2024-04-05");
        pedido4.setPedPagado(true);
        pedido4.setCliente(cliente4);
        DetallePedido detalle4 = new DetallePedido();
        detalle4.setProducto(producto5);
        detalle4.setCantidad(2);
        detalle4.setPrecioUnitario(producto5.getProPrecio());
        detalle4.setSubtotal(producto5.getProPrecio().multiply(new BigDecimal("2")));
        pedido4.addDetalle(detalle4);
        pedido4.setPedSubtotal(detalle4.getSubtotal());
        pedido4.setPedDescuento(new BigDecimal("15.00"));
        pedido4.setPedTotal(detalle4.getSubtotal().subtract(new BigDecimal("15.00")));
        pedidoRepository.save(pedido4);

        Pedido pedido5 = new Pedido();
        pedido5.setPedFechaCompra("2024-05-12");
        pedido5.setPedPagado(false);
        pedido5.setCliente(cliente5);
        DetallePedido detalle5 = new DetallePedido();
        detalle5.setProducto(producto6);
        detalle5.setCantidad(1);
        detalle5.setPrecioUnitario(producto6.getProPrecio());
        detalle5.setSubtotal(producto6.getProPrecio());
        pedido5.addDetalle(detalle5);
        pedido5.setPedSubtotal(detalle5.getSubtotal());
        pedido5.setPedDescuento(new BigDecimal("8.00"));
        pedido5.setPedTotal(detalle5.getSubtotal().subtract(new BigDecimal("8.00")));
        pedidoRepository.save(pedido5);

        Pedido pedido6 = new Pedido();
        pedido6.setPedFechaCompra("2024-06-18");
        pedido6.setPedPagado(true);
        pedido6.setCliente(cliente6);
        DetallePedido detalle6a = new DetallePedido();
        detalle6a.setProducto(producto1);
        detalle6a.setCantidad(1);
        detalle6a.setPrecioUnitario(producto1.getProPrecio());
        detalle6a.setSubtotal(producto1.getProPrecio());
        pedido6.addDetalle(detalle6a);
        DetallePedido detalle6b = new DetallePedido();
        detalle6b.setProducto(producto5);
        detalle6b.setCantidad(1);
        detalle6b.setPrecioUnitario(producto5.getProPrecio());
        detalle6b.setSubtotal(producto5.getProPrecio());
        pedido6.addDetalle(detalle6b);
        pedido6.setPedSubtotal(detalle6a.getSubtotal().add(detalle6b.getSubtotal()));
        pedido6.setPedDescuento(new BigDecimal("12.00"));
        pedido6.setPedTotal(pedido6.getPedSubtotal().subtract(new BigDecimal("12.00")));
        pedidoRepository.save(pedido6);

        log.info(">>> DataInitializer: pedidos base cargados.");
    }
}