package com.example.venta_sneackers.Service;

import com.example.venta_sneackers.dto.ClienteRequestDTO;
import com.example.venta_sneackers.dto.ClienteResponseDTO;
import com.example.venta_sneackers.model.Cliente;
import com.example.venta_sneackers.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<ClienteResponseDTO> obtenerTodos() {
        return repository.findAll().stream().map(cliente ->
            new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getCliNombre(),
                cliente.getCliDireccion(),
                cliente.getCliEstado()
            )).collect(Collectors.toList());
    }

    public List<ClienteResponseDTO> obtenerPorIdCliente(Long idCliente) {
        return repository.findByIdCliente(idCliente).stream().map(cliente ->
            new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getCliNombre(),
                cliente.getCliDireccion(),
                cliente.getCliEstado()
            )).collect(Collectors.toList());
    }

    public Optional<ClienteResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(cliente ->
            new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getCliNombre(),
                cliente.getCliDireccion(),
                cliente.getCliEstado()
            ));
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setCliNombre(dto.getCliNombre());
        cliente.setCliDireccion(dto.getCliDireccion());
        cliente.setCliEstado(dto.getCliEstado());

        Cliente saved = repository.save(cliente);
        return new ClienteResponseDTO(
            saved.getIdCliente(),
            saved.getCliNombre(),
            saved.getCliDireccion(),
            saved.getCliEstado()
        );
    }

    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = repository.findById(id).orElseThrow();
        cliente.setCliNombre(dto.getCliNombre());
        cliente.setCliDireccion(dto.getCliDireccion());
        cliente.setCliEstado(dto.getCliEstado());

        Cliente updated = repository.save(cliente);
        return new ClienteResponseDTO(
            updated.getIdCliente(),
            updated.getCliNombre(),
            updated.getCliDireccion(),
            updated.getCliEstado()
        );
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public List<ClienteResponseDTO> buscarPorNombre(String cliNombre) {
        return repository.findByCliNombreContainingIgnoreCase(cliNombre).stream().map(cliente ->
            new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getCliNombre(),
                cliente.getCliDireccion(),
                cliente.getCliEstado()
            )).collect(Collectors.toList());
    }

    public List<ClienteResponseDTO> buscarPorDireccion(String cliDireccion) {
        return repository.findByCliDireccion(cliDireccion).stream().map(cliente ->
            new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getCliNombre(),
                cliente.getCliDireccion(),
                cliente.getCliEstado()
            )).collect(Collectors.toList());
    }

    public List<ClienteResponseDTO> buscarPorEstado(String cliEstado) {
        return repository.findByCliEstadoContainingIgnoreCase(cliEstado).stream().map(cliente ->
            new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getCliNombre(),
                cliente.getCliDireccion(),
                cliente.getCliEstado()
            )).collect(Collectors.toList());
    }

	
}
