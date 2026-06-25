package com.example.venta_sneackers.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.venta_sneackers.dto.ModeloRequestDTO;
import com.example.venta_sneackers.dto.ModeloResponseDTO;
import com.example.venta_sneackers.model.Modelo;
import com.example.venta_sneackers.repository.ModeloRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModeloService {

    private final ModeloRepository modeloRepository;
    
    // ── MAPEO PRIVADO: Entidad → ResponseDTO ─────────
    private ModeloResponseDTO mapToDTO(Modelo modelo) {
        return new ModeloResponseDTO(
                modelo.getIdModelo(),
                modelo.getModDescripcion(),
                modelo.getModNombre(),
                modelo.getModTemporada(),
                modelo.getModAnioLanzamiento(),
                modelo.getModEdicionLimitada()
        );

    }

    // 1.OBTENER TODOS
    public List<ModeloResponseDTO> obtenerTodos() {
        return modeloRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 2.OBTENER POR ID
    @SuppressWarnings("null")
    public Optional<ModeloResponseDTO> obtenerPorId(Long id) {
        return modeloRepository
            .findById(id)
            .map(this::mapToDTO);
    }

    // 3.GUARDAR
    public ModeloResponseDTO guardar(ModeloRequestDTO dto) {
        Modelo modelo = new Modelo(
                null,
                dto.getModDescripcion(),
                dto.getModNombre(),
                dto.getModTemporada(),
                dto.getModAnioLanzamiento(),
                dto.getModEdicionLimitada()
        );
        return mapToDTO(modeloRepository.save(modelo));
    }

    // 4.ACTUALIZAR
    @SuppressWarnings("null")
    public Optional<ModeloResponseDTO> actualizar(Long id, ModeloRequestDTO dto) {
        return modeloRepository.findById(id).map(existente -> {
            existente.setModDescripcion(dto.getModDescripcion());
            existente.setModNombre(dto.getModNombre());
            existente.setModTemporada(dto.getModTemporada());
            existente.setModAnioLanzamiento(dto.getModAnioLanzamiento());
            existente.setModEdicionLimitada(dto.getModEdicionLimitada());
            return mapToDTO(modeloRepository.save(existente));
        });
    }

    
    // 5.ELIMINAR
    @SuppressWarnings("null")
    public void eliminar(Long id) {
        modeloRepository.deleteById(id);
    }

    // 6.BUSCAR POR NOMBRE
    public List<ModeloResponseDTO> buscarPorNombre(String nombre) {
        return modeloRepository.findByModNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 7.BUSCAR POR TEMPORADA
    public List<ModeloResponseDTO> buscarPorTemporada(String temporada) {
        return modeloRepository.findByModTemporada(temporada)
                .stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    // 8.BUSCAR POR EDICION LIMITADA
    public List<ModeloResponseDTO> buscarPorEdicionLimitada(Boolean edicionLimitada) {
        return modeloRepository.findByModEdicionLimitada(edicionLimitada)
                .stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    // 9.BUSCAR POR AÑO LANZAMIENTO
    public List<ModeloResponseDTO> buscarPorAnioLanzamiento(Integer anio) {
        return modeloRepository.findByModAnioLanzamiento(anio)
                .stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    // 10.BUSCAR POR TEMPORADA ORDENADO
    public List<ModeloResponseDTO> buscarPorTemporadaOrdenado(String temporada) {
        return modeloRepository.findByTemporadaOrdenado(temporada)
                .stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }
}
