package com.example.venta_sneackers.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.venta_sneackers.dto.PedidoDetalleResponseDTO;
import com.example.venta_sneackers.model.DetallePedido;
import com.example.venta_sneackers.repository.DetallePedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    public List<PedidoDetalleResponseDTO> getDetallesByPedidoId(Long pedidoId) {
        return detallePedidoRepository.findById(pedidoId).stream()
                .map(this::toResponseDTO)
                        .toList();
    }

    //OBTENER TODOS LOS DETALLES
    public List<PedidoDetalleResponseDTO> obtenerTodos() {
        return detallePedidoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //OBTENER DETALLE PEDIDO POR ID
    public java.util.Optional<PedidoDetalleResponseDTO> obtenerPorId(Long id) {
                return detallePedidoRepository.findById(id).map(this::toResponseDTO);
    }

    //BUSCAR DETALLES POR ID DE PEDIDO
    public List<PedidoDetalleResponseDTO> buscarPorIdPedido(Long pedidoId) {
        return detallePedidoRepository.findByPedido_IdPedido(pedidoId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR DETALLES POR ID DE PRODUCTO
    public List<PedidoDetalleResponseDTO> buscarPorIdProducto(Long productoId) {
        return detallePedidoRepository.findByProducto_IdProducto(productoId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR DETALLES POR ID DE CLIENTE
    public List<PedidoDetalleResponseDTO> buscarPorIdCliente(Long clienteId) {
        return detallePedidoRepository.findByPedido_Cliente_IdCliente(clienteId).stream()
                .map(this::toResponseDTO)
                .toList();
    }



    //GUARDAR UN NUEVO DETALLE DE PEDIDO
    public PedidoDetalleResponseDTO guardar(PedidoDetalleResponseDTO dto) {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setCantidad(dto.getCantidad());
        detallePedido.setPrecioUnitario(dto.getPrecioUnitario());
        detallePedido.setSubtotal(dto.getSubtotal());

        DetallePedido saved = detallePedidoRepository.save(detallePedido);
        return toResponseDTO(saved);
    }

    //ELIMINAR DETALLE DE PEDIDO POR ID
    public void eliminar(Long id) {
        detallePedidoRepository.deleteById(id);
    }

        private PedidoDetalleResponseDTO toResponseDTO(DetallePedido detalle) {
                Long pedidoId = detalle.getPedido() != null ? detalle.getPedido().getIdPedido() : null;
                Long clienteId = (detalle.getPedido() != null && detalle.getPedido().getCliente() != null)
                                ? detalle.getPedido().getCliente().getIdCliente()
                                : null;
                String clienteNombre = (detalle.getPedido() != null && detalle.getPedido().getCliente() != null)
                                ? detalle.getPedido().getCliente().getCliNombre()
                                : null;
                Long productoId = detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null;
                String productoNombre = detalle.getProducto() != null ? detalle.getProducto().getProNombre() : null;

                return new PedidoDetalleResponseDTO(
                                detalle.getIdDetallePedido(),
                                pedidoId,
                                pedidoId != null ? "PED-" + pedidoId : null,
                                clienteId,
                                clienteNombre,
                                productoId,
                                productoNombre,
                                detalle.getCantidad(),
                                detalle.getPrecioUnitario(),
                                detalle.getSubtotal()
                );
        }
    
}
