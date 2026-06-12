package com.example.venta_sneackers.controller;

import java.util.List;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.TallaService;
import com.example.venta_sneackers.dto.TallaResponseDTO;

import jakarta.annotation.Generated;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/V1/tallas")
@RequiredArgsConstructor

public class TallaController {

    private final TallaService tallaService;


     //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping
    public ResponseEntity<List<TallaResponseDTO>> obtenerTodos() {
    return ResponseEntity.ok(tallaService.obtenerTodos());

    @GetMapping("/{id}")
    public ResponseEntity<TallaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return tallaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
  

        
}
