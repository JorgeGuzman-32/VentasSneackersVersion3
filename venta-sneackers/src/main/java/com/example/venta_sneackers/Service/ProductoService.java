package com.example.venta_sneackers.Service;

import com.example.venta_sneackers.dto.ProductoRequestDTO;
import com.example.venta_sneackers.dto.ProductoResponseDTO;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.model.Producto;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.ModeloRepository;
import com.example.venta_sneackers.repository.ProductoRepository;
import com.example.venta_sneackers.repository.TallaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;
    private final TallaRepository tallaRepository;
    private final ModeloRepository modeloRepository;

    private ProductoResponseDTO toResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getProNombre(),
                producto.getProDescripcion(),
                producto.getProFechaIngreso(),
                producto.getProPrecio(),
                producto.getStock(),
                producto.getProMarca(),
                producto.getProColor(),
                producto.getProGenero(),
                producto.getProEdad(),
                producto.getTalla().getId(),
                producto.getModelo().getIdModelo()

        );
    }

    public List<ProductoResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("null")
    public Optional<ProductoResponseDTO> obtenerProdPorId(Long id) {
        return repository.findById(id)
                .map(this::toResponseDTO);
    }

    public List<ProductoResponseDTO> buscarPorNombre(String proNombre) {
        return repository.findByProNombreContainingIgnoreCase(proNombre).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorMarca(String proMarca) {
        return repository.findByProMarca(proMarca).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> proColor(String proColor) {
        return repository.findByProColor(proColor).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> proGenero(String proGenero) {
        return repository.findByProGenero(proGenero).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public boolean actualizarStock(Long id, Integer nuevoStock) {
        @SuppressWarnings("null")
        Optional<Producto> optionalProducto = repository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setStock(nuevoStock);
            repository.save(producto);
            return true;
        }
        return false;
    }

    public boolean actualizarPrecio(Long id, BigDecimal nuevoPrecio) {
        @SuppressWarnings("null")
        Optional<Producto> optionalProducto = repository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setProPrecio(nuevoPrecio);
            repository.save(producto);
            return true;
        }
        return false;
    }

    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        Talla talla = tallaRepository.findById(dto.getTallaId())
                .orElseThrow(() -> new RuntimeException("Talla no encontrada con id: " + dto.getTallaId()));

        Modelo modelo = modeloRepository.findById(dto.getModeloId())
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado con id: " + dto.getModeloId()));

        Producto producto = new Producto();
        producto.setProNombre(dto.getProNombre());
        producto.setProDescripcion(dto.getProDescripcion());
        producto.setProFechaIngreso(dto.getProFechaIngreso());
        producto.setProPrecio(dto.getProPrecio());
        producto.setStock(dto.getStock());
        producto.setProMarca(dto.getProMarca());
        producto.setProColor(dto.getProColor());
        producto.setProGenero(dto.getProGenero());
        producto.setProEdad(dto.getProEdad());
        producto.setTalla(talla);
        producto.setModelo(modelo);

        return toResponseDTO(repository.save(producto));
    }

public boolean eliminarProducto(Long id) {
    if (repository.existsById(id)) {
        repository.deleteById(id);
        return true;
    }
    return false;
}
}
