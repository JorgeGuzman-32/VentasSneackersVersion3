package com.example.venta_sneackers.Service;

import com.example.venta_sneackers.dto.TallaRequestDTO;
import com.example.venta_sneackers.dto.TallaResponseDTO;
import com.example.venta_sneackers.model.Talla;
import com.example.venta_sneackers.repository.TallaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TallaService {
    private final TallaRepository repository;

    public List<TallaResponseDTO> obtenerTodos() {
        return repository.findAll().stream().map(talla ->
            new TallaResponseDTO(
                talla.getId(),
                talla.getTallNombre(),
                talla.getUnidad()
            )).collect(Collectors.toList());
    }

    @SuppressWarnings("null")
    public Optional<TallaResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(talla ->
            new TallaResponseDTO(
                talla.getId(),
                talla.getTallNombre(),
                talla.getUnidad()
            ));
    }

    public TallaResponseDTO guardar(TallaRequestDTO dto) {
        Talla talla = new Talla();
        talla.setTallNombre(dto.getTallNombre());
        talla.setUnidad(dto.getUnidad());
        Talla saved = repository.save(talla);
        return new TallaResponseDTO(
            saved.getId(),
            saved.getTallNombre(),
            saved.getUnidad()
        );
    }

    public TallaResponseDTO actualizar(Long id, TallaResponseDTO dto) {
        @SuppressWarnings("null")
        Talla talla = repository.findById(id).orElseThrow();
        talla.setTallNombre(dto.getTallNombre());
        talla.setUnidad(dto.getUnidad());
        Talla updated = repository.save(talla);

        return new TallaResponseDTO(
            updated.getId(),
            updated.getTallNombre(),
            updated.getUnidad()
        );
    }
    

    @SuppressWarnings("null")
    public void eliminar(Long id) {
        repository.deleteById(id);
    }



}
