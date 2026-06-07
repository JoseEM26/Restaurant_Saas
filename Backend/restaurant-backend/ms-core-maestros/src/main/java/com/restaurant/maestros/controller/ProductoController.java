package com.restaurant.maestros.controller;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.maestros.dto.request.ProductoRequest;
import com.restaurant.maestros.dto.response.ProductoResponse;
import com.restaurant.maestros.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maestros/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos del menú")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public ApiResponse<List<ProductoResponse>> listar() {
        return ApiResponse.ok(productoService.listarTodos());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar productos disponibles")
    public ApiResponse<List<ProductoResponse>> listarDisponibles() {
        return ApiResponse.ok(productoService.listarDisponibles());
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar productos por categoría")
    public ApiResponse<List<ProductoResponse>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ApiResponse.ok(productoService.listarPorCategoria(categoriaId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ApiResponse<ProductoResponse> buscarPorId(@PathVariable Long id) {
        return ApiResponse.ok(productoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo producto")
    public ApiResponse<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        return ApiResponse.ok("Producto creado", productoService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto")
    public ApiResponse<ProductoResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody ProductoRequest request) {
        return ApiResponse.ok("Producto actualizado", productoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto (baja lógica)")
    public ApiResponse<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ApiResponse.ok("Producto eliminado", null);
    }
}
