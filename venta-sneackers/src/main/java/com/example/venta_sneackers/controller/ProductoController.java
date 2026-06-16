package com.example.venta_sneackers.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.ProductoService;
import com.example.venta_sneackers.dto.ProductoRequestDTO;
import com.example.venta_sneackers.dto.ProductoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/V1/productos")
@Tag(name = "Producto", description = "Endpoints para gestionar los productos disponibles en la tienda")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Obtener todos los productos
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }


    //Obtener producto por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
            Content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    //Buscar productos por nombre
    @GetMapping("/buscar/{proNombre}")
    @Operation(summary = "Buscar productos por nombre", description = "Devuelve una lista de productos que coinciden con el nombre proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            Content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@PathVariable String proNombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(proNombre));
    }

    //Buscar productos por marca
    @GetMapping("/buscar/marca/{proMarca}")
    @Operation(summary = "Buscar productos por marca", description = "Devuelve una lista de productos que coinciden con la marca proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            Content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorMarca(@PathVariable String proMarca) {
        return ResponseEntity.ok(productoService.buscarPorMarca(proMarca));
    }   
    

    //Buscar productos por talla
    @GetMapping("/buscar/color/{proColor}")
    @Operation(summary = "Buscar productos por color", description = "Devuelve una lista de productos que coinciden con el color proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            Content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorColor(@PathVariable String proColor) {
        return ResponseEntity.ok(productoService.proColor(proColor));
    }           


    //Buscar productos por género
    @GetMapping("/buscar/genero/{proGenero}")
    @Operation(summary = "Buscar productos por género", description = "Devuelve una lista de productos que coinciden con el género proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            Content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorGenero(@PathVariable String proGenero) {
        return ResponseEntity.ok(productoService.proGenero(proGenero));

    }   
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                 /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Actualizar stock de producto
    @PutMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock de producto", description = "Permite actualizar el stock de un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente",
            Content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, Integer nuevoStock) {    
        boolean actualizado = productoService.actualizarStock(id, nuevoStock);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }       

    //Actualizar precio de producto
    @PutMapping("/{id}/precio")
    @Operation(summary = "Actualizar precio de producto", description = "Permite actualizar el precio de un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precio actualizado exitosamente",
            Content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> actualizarPrecio(@PathVariable Long id, BigDecimal nuevoPrecio) {    
        boolean actualizado = productoService.actualizarPrecio(id, nuevoPrecio);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }       


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                 ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Crear nuevo producto
    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Permite crear un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            Content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO dto)   {
        ProductoResponseDTO created = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);     

    }
    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Permite eliminar un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente",
            Content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {           
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
}
