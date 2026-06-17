package com.example.venta_sneackers.repository;

import com.example.venta_sneackers.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByCliNombreContainingIgnoreCase(String cliNombre);

    List<Cliente> findByCliNombre(String cliNombre);

    List<Cliente> findByCliDireccion(String cliDireccion);

    List<Cliente> findByCliEstadoContainingIgnoreCase(String cliEstado);

    List<Cliente> findByIdCliente(Long idCliente);

}
