package com.example.venta_sneackers.repository;

import com.example.venta_sneackers.model.Talla;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TallaRepository extends JpaRepository<Talla, Long> {

    List<Talla> findByTallNombre(String tallNombre);
}
