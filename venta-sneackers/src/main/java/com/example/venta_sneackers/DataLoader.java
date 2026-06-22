package com.example.venta_sneackers;

import com.example.venta_sneackers.model;
import com.example.venta_sneackers.repository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Crear productos 
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence());
            producto.setPrecio(faker.number().randomDouble(2, 10, 100));
            producto.setStock(faker.number().numberBetween(1, 50));
            productoRepository.save(producto);
        }

        // Crear clientes 
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().fullName());
            cliente.setEmail(faker.internet().emailAddress());
            cliente.setTelefono(faker.phoneNumber().phoneNumber());
            clienteRepository.save(cliente);
        }

        // Crear pedidos 
        List<Cliente> clientes = clienteRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        for (int i = 0; i < 5; i++) {
            Pedido pedido = new Pedido();
            pedido.setCliente(clientes.get(random.nextInt(clientes.size())));
            pedido.setFecha(new Date());
            pedidoRepository.save(pedido);

            // Crear detalles de pedido para cada pedido
            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPedido(pedido);
                detallePedido.setProducto(productos.get(random.nextInt(productos.size())));
                detallePedido.setCantidad(faker.number().numberBetween(1, 5));
                detallePedidoRepository.save(detallePedido);

            // Actualizar el stock del producto
                Producto producto = detallePedido.getProducto();
                producto.setStock(producto.getStock() - detallePedido.getCantidad());
                productoRepository.save(producto);
                
            // Actualizar el total del pedido
                double total = pedido.getTotal() + (detallePedido.getProducto().getPrecio() * detallePedido.getCantidad());
                pedido.setTotal(total);
                pedidoRepository.save(pedido);

            // Guardar el detalle del pedido
                detallePedidoRepository.save(detallePedido);    
            
            // Guardar el pedido con los detalles actualizados
                pedidoRepository.save(pedido);

            // Guardar el detalle del pedido
                detallePedidoRepository.save(detallePedido);
            }
        }
    }

}
