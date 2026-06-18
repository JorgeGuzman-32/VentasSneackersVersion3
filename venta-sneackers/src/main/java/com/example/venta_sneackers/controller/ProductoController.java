package com.example.venta_sneackers.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta_sneackers.Service.ProductoService;
import com.example.venta_sneackers.dto.ProductoRequestDTO;
import com.example.venta_sneackers.dto.ProductoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@ResponseBody
@RequestMapping("/api/V1/productos")
@Tag(name = "Producto", description = "Endpoints para gestionar los productos disponibles en la tienda")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 GETs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Obtener todos los productos
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error al obtener los productos"
                }
            """)))
    })
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }


    //Obtener producto por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Producto no encontrado"
                }
            """)))
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        java.util.Optional<ProductoResponseDTO> productoOpt = productoService.obtenerPorId(id);
        if (productoOpt.isPresent()) {
            return ResponseEntity.ok(productoOpt.get());
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Producto no encontrado"
            );
            return ResponseEntity.status(404).body(error);
        }
    }

    //Buscar productos por nombre
    @GetMapping("/buscar/{proNombre}")
    @Operation(summary = "Buscar productos por nombre", description = "Devuelve una lista de productos que coinciden con el nombre proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con el nombre proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron productos con el nombre proporcionado."
                }
            """)))
    })
    public ResponseEntity<?> buscarPorNombre(@PathVariable String proNombre) {
        List<ProductoResponseDTO> productos = productoService.buscarPorNombre(proNombre);
        if (productos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron productos con el nombre '" + proNombre + "'"
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(productos);
    }

    //Buscar productos por marca
    @GetMapping("/buscar/marca/{proMarca}")
    @Operation(summary = "Buscar productos por marca", description = "Devuelve una lista de productos que coinciden con la marca proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con la marca proporcionada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron productos con la marca proporcionada."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error al buscar los productos por marca"
                }
            """)))
    })
    public ResponseEntity<?> buscarPorMarca(@PathVariable String proMarca) {
        List<ProductoResponseDTO> productos = productoService.buscarPorMarca(proMarca);
        if (productos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron productos con la marca '" + proMarca + "'"
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(productos);
    }   
    

    //Buscar productos por talla
    @GetMapping("/buscar/color/{proColor}")
    @Operation(summary = "Buscar productos por color", description = "Devuelve una lista de productos que coinciden con el color proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con el color proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron productos con el color proporcionado."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error al buscar los productos por color"
                }
            """)))
    })
    public ResponseEntity<?> buscarPorColor(@PathVariable String proColor) {
        List<ProductoResponseDTO> productos = productoService.proColor(proColor);
        if (productos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron productos con el color '" + proColor + "'"
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(productos);
    }           


    //Buscar productos por género
    @GetMapping("/buscar/genero/{proGenero}")
    @Operation(summary = "Buscar productos por género", description = "Devuelve una lista de productos que coinciden con el género proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponseDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con el género proporcionado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "No se encontraron productos con el género proporcionado."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error al buscar los productos por género"
                }
            """)))
    })
    public ResponseEntity<?> buscarPorGenero(@PathVariable String proGenero) {
        List<ProductoResponseDTO> productos = productoService.proGenero(proGenero);
        if (productos.isEmpty()) {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "No se encontraron productos con el género '" + proGenero + "'"
            );
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(productos);
    }   
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 PUTs                                 /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Actualizar stock de producto
    @PutMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock de producto", description = "Permite actualizar el stock de un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Producto no encontrado"
                }
            """))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "El nuevo stock debe ser un número entero no negativo."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, Integer nuevoStock) {    
        boolean actualizado = productoService.actualizarStock(id, nuevoStock);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Producto no encontrado"
            );
            return ResponseEntity.status(404).body(error);
        }
    }       

    //Actualizar precio de producto
    @PutMapping("/{id}/precio")
    @Operation(summary = "Actualizar precio de producto", description = "Permite actualizar el precio de un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precio actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Producto no encontrado"
                }
            """))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "El nuevo precio debe ser un número decimal positivo."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> actualizarPrecio(@PathVariable Long id, BigDecimal nuevoPrecio) {    
        boolean actualizado = productoService.actualizarPrecio(id, nuevoPrecio);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Producto no encontrado"
            );
            return ResponseEntity.status(404).body(error);
        }
    }       


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 POSTs                                /////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Crear nuevo producto
    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Permite crear un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Solicitud inválida",
                    "descripcion": "Verifique que todos los campos requeridos estén presentes y sean válidos."
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error al crear el producto"
                }
            """)))
    })
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO created = productoService.crearProducto(dto);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
             
    }
    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////                 DELETEs                               //////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Permite eliminar un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "No encontrado",
                    "descripcion": "Producto no encontrado"
                }
            """))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                {
                    "error": "Error interno",
                    "descripcion": "Ocurrió un error al eliminar el producto"
                }
            """)))
    })
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {          
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            Map<String, String> error = Map.of(
                "error", "No encontrado",
                "descripcion", "Producto no encontrado"
            );
            return ResponseEntity.status(404).body(error);
        }
    }   
}
