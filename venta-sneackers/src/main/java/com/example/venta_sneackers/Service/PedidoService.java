package com.example.venta_sneackers.Service;

import com.example.venta_sneackers.dto.PedidoDetalleRequestDTO;
import com.example.venta_sneackers.dto.PedidoDetalleResponseDTO;
import com.example.venta_sneackers.dto.PedidoRequestDTO;
import com.example.venta_sneackers.dto.PedidoResponseDTO;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.model.DetallePedido;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.repository.ClienteRepository;
import com.example.venta_sneackers.repository.PedidoRepository;
import com.example.venta_sneackers.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public PedidoResponseDTO guardar(PedidoRequestDTO requestDTO) {
        log.info("Guardando nuevo pedido para cliente: {}", requestDTO.getClienteId());

        // Validar que items no sea nulo o vacÃƒÂ­o
        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un item");
        }

        // Obtener cliente
        Cliente cliente = clienteRepository.findById(requestDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setPedFechaCompra(requestDTO.getPedFechaCompra());
        pedido.setPedPagado(requestDTO.getPedPagado());

        // Procesar detalles
        BigDecimal subtotal = BigDecimal.ZERO;
        List<DetallePedido> detalles = new java.util.ArrayList<>();

        for (PedidoDetalleRequestDTO detalleDTO : requestDTO.getItems()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Producto no encontrado: " + detalleDTO.getProductoId()));

            if (detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());

            // Calcular subtotal del item
            BigDecimal itemSubtotal = detalleDTO.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
            detalle.setSubtotal(itemSubtotal);

            detalles.add(detalle);
            subtotal = subtotal.add(itemSubtotal);
        }

        pedido.setDetalles(detalles);
        pedido.setPedSubtotal(subtotal);
        pedido.setPedDescuento(requestDTO.getPedDescuento() != null ? requestDTO.getPedDescuento() : BigDecimal.ZERO);
        pedido.setPedTotal(subtotal.subtract(pedido.getPedDescuento()));

        Pedido savedPedido = repository.save(pedido);
        log.info("Pedido guardado con ID: {}", savedPedido.getIdPedido());

        return toResponseDTO(savedPedido);
    }

    @Transactional
    public PedidoResponseDTO actualizar(Long id, PedidoRequestDTO requestDTO) {
        log.info("Actualizando pedido: {}", id);

        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un item");
        }

        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        pedido.setPedFechaCompra(requestDTO.getPedFechaCompra());
        pedido.setPedPagado(requestDTO.getPedPagado());

        // Limpiar detalles antiguos
        pedido.getDetalles().clear();

        // Procesar nuevos detalles
        BigDecimal subtotal = BigDecimal.ZERO;

        for (PedidoDetalleRequestDTO detalleDTO : requestDTO.getItems()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Producto no encontrado: " + detalleDTO.getProductoId()));

            if (detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());

            BigDecimal itemSubtotal = detalleDTO.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
            detalle.setSubtotal(itemSubtotal);

            pedido.getDetalles().add(detalle);
            subtotal = subtotal.add(itemSubtotal);
        }

        pedido.setPedSubtotal(subtotal);
        pedido.setPedDescuento(requestDTO.getPedDescuento() != null ? requestDTO.getPedDescuento() : BigDecimal.ZERO);
        pedido.setPedTotal(subtotal.subtract(pedido.getPedDescuento()));

        Pedido updatedPedido = repository.save(pedido);
        log.info("Pedido actualizado: {}", id);

        return toResponseDTO(updatedPedido);
    }

    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
        log.info("Pedido eliminado: {}", id);
    }

    public List<PedidoDetalleResponseDTO> obtenerTodosDetalles() {
        return repository.findAll().stream()
                .flatMap(pedido -> pedido.getDetalles().stream())
                .map(this::toDetalleResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleResponseDTO> obtenerDetallesPorPedidoId(Long pedidoId) {
        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        return pedido.getDetalles().stream()
                .map(this::toDetalleResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleResponseDTO> obtenerDetallesPorProductoId(Long productoId) {
        return repository.findAll().stream()
                .flatMap(pedido -> pedido.getDetalles().stream())
                .filter(detalle -> detalle.getProducto().getIdProducto().equals(productoId))
                .map(this::toDetalleResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDetalleResponseDTO agregarDetalleAPedido(Long pedidoId, PedidoDetalleRequestDTO detalleDTO) {
        log.info("Agregando detalle a pedido: {}", pedidoId);

        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado: " + detalleDTO.getProductoId()));

        if (detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());

        BigDecimal itemSubtotal = detalleDTO.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
        detalle.setSubtotal(itemSubtotal);

        pedido.getDetalles().add(detalle);

        // Recalcular totales
        BigDecimal newSubtotal = pedido.getDetalles().stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setPedSubtotal(newSubtotal);
        pedido.setPedTotal(newSubtotal.subtract(pedido.getPedDescuento()));

        repository.save(pedido);
        log.info("Detalle agregado: {}", pedidoId);

        return toDetalleResponseDTO(detalle);
    }

    public List<Pedido> buscarPorTotalDeCompra(double totalBuscado) {
        return repository.findAll().stream()
                .filter(pedido -> pedido.getPedTotal().doubleValue() == totalBuscado)
                .collect(Collectors.toList());
    }

    @Transactional
    public void actualizarEstadoPago(Long id, boolean pagado) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        pedido.setPedPagado(pagado);
        repository.save(pedido);
        log.info("Estado de pago actualizado para pedido: {}", id);
    }

    public List<Pedido> getPedidosBetweenFechas(String startFecha, String endFecha) {
        return repository.findAll().stream()
                .filter(pedido -> {
                    String fecha = pedido.getPedFechaCompra();
                    return fecha.compareTo(startFecha) >= 0 && fecha.compareTo(endFecha) <= 0;
                })
                .collect(Collectors.toList());
    }

    public List<Pedido> getPedidosByClienteAndEstado(Long clienteId, String estado) {
        boolean estadoPagado = "pagado".equalsIgnoreCase(estado);
        return repository.findAll().stream()
                .filter(pedido -> pedido.getCliente().getIdCliente().equals(clienteId)
                        && pedido.getPedPagado().equals(estadoPagado))
                .collect(Collectors.toList());
    }

    public List<Pedido> getAllPedidos() {
        return repository.findAll();
    }

    public Pedido getPedidoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    }

    public List<Pedido> getPedidosByFecha(String fecha) {
        return repository.findAll().stream()
                .filter(pedido -> pedido.getPedFechaCompra().equals(fecha))
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<PedidoDetalleResponseDTO> items = pedido.getDetalles().stream()
                .map(this::toDetalleResponseDTO)
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                "PED-" + pedido.getIdPedido(),
                pedido.getPedFechaCompra(),
                pedido.getPedSubtotal(),
                pedido.getPedDescuento(),
                pedido.getPedTotal(),
                pedido.getCliente().getIdCliente(),
                items
        );
    }

    private PedidoDetalleResponseDTO toDetalleResponseDTO(DetallePedido detalle) {
        return new PedidoDetalleResponseDTO(
                detalle.getIdDetallePedido(),
                detalle.getPedido().getIdPedido(),
                "PED-" + detalle.getPedido().getIdPedido(),
                detalle.getPedido().getCliente().getIdCliente(),
                detalle.getPedido().getCliente().getCliNombre(),
                detalle.getProducto().getIdProducto(),
                detalle.getProducto().getProNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
}
