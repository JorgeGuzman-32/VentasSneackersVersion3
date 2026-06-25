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

    //MAPEO DE ENTIDAD A DTO
    private ClienteResponseDTO mapToDTO(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getIdCliente(),
            cliente.getCliNombre(),
            cliente.getCliDireccion(),
            cliente.getCliEstado()
        );
    }

    // OBTENER TODOS LOS CLIENTES
    public List<ClienteResponseDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // OBTENER CLIENTES POR ID DE CLIENTE
    public Optional<ClienteResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id)
            .map(this::mapToDTO);
    }
    

    // OBTENER CLIENTES POR ID DE CLIENTE
    public List<ClienteResponseDTO> obtenerPorIdCliente(Long idCliente) {
        return repository.findByIdCliente(idCliente)
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    //GUARDA UN NUEVO CLIENTE
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

    //ACTUALIZA UN CLIENTE EXISTENTE
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        @SuppressWarnings("null")
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

    //ELIMINA UN CLIENTE POR ID
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    //BUSCAR CLIENTES POR NOMBRE
    public List<ClienteResponseDTO> buscarPorNombre(String cliNombre) {
        return repository.findByCliNombreContainingIgnoreCase(cliNombre)
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }


    //BUSCAR CLIENTES POR DIRECCION        
    public List<ClienteResponseDTO> buscarPorDireccion(String cliDireccion) {
        return repository.findByCliDireccion(cliDireccion)
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    //BUSCAR CLIENTES POR ESTADO
    public List<ClienteResponseDTO> buscarPorEstado(String cliEstado) {
        return repository.findByCliEstadoContainingIgnoreCase(cliEstado)
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
        
}
