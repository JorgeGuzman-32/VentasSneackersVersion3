package com.example.venta_sneackers.Service;

import com.example.venta_sneackers.dto.PedidoRequestDTO;
import com.example.venta_sneackers.dto.PedidoResponseDTO;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.model.Pedido;
import com.example.venta_sneackers.repository.ClienteRepository;
import com.example.venta_sneackers.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;

    private String numeroPedido(Pedido pedido) {
        return String.valueOf(pedido.getIdPedido());
    }

    // Método para convertir Pedido a PedidoResponseDTO
    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                numeroPedido(pedido),
                pedido.getPedFechaCompra(),
                pedido.getPedSubtotal(),
                pedido.getPedDescuento(),
                pedido.getPedTotal(),
                pedido.getCliente().getIdCliente()
        );
    }

    // OBTENER TODOS LOS PEDIDOS
    public List<PedidoResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // OBTENER PEDIDOS POR ESTADO DE PAGO
    public List<PedidoResponseDTO> buscarPorPagado(boolean pedPagado) {
        return repository.findByPedPagado(pedPagado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // OBTENER PEDIDO POR ID
    public Optional<PedidoResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toResponseDTO);
    }


    // GUARDAR NUEVO PEDIDO
    public PedidoResponseDTO guardar(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow();

        Pedido pedido = new Pedido();
        pedido.setPedFechaCompra(dto.getPedFechaCompra());
        pedido.setPedSubtotal(dto.getPedSubtotal());
        pedido.setPedDescuento(dto.getPedDescuento());
        pedido.setPedTotal(dto.getPedTotal());
        pedido.setPedPagado(Boolean.TRUE.equals(dto.getPedPagado()));
        pedido.setCliente(cliente);

        Pedido saved = repository.save(pedido);
        return toResponseDTO(saved);
    }


    // ACTUALIZAR PEDIDO EXISTENTE
    public PedidoResponseDTO actualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = repository.findById(id).orElseThrow();
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow();

        pedido.setPedFechaCompra(dto.getPedFechaCompra());
        pedido.setPedSubtotal(dto.getPedSubtotal());
        pedido.setPedDescuento(dto.getPedDescuento());
        pedido.setPedTotal(dto.getPedTotal());
        pedido.setPedPagado(Boolean.TRUE.equals(dto.getPedPagado()));
        pedido.setCliente(cliente);

        Pedido updated = repository.save(pedido);
        return toResponseDTO(updated);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // ACTUALIZAR ESTADO DE PAGO
    public PedidoResponseDTO actualizarEstadoPago(Long id, boolean pagado) {
        Pedido pedido = repository.findById(id).orElseThrow();
        pedido.setPedPagado(pagado);
        Pedido updated = repository.save(pedido);
        return toResponseDTO(updated);
    

    // OBTENER PEDIDOS POR ID DE CLIENTE
    public List<PedidoResponseDTO> obtenerPorClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        
}
