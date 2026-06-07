package com.restaurant.maestros.controller;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.maestros.dto.request.CategoriaRequest;
import com.restaurant.maestros.dto.response.CategoriaResponse;
import com.restaurant.maestros.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maestros/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Gestión de categorías del menú")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar todas las categorías")
    public ApiResponse<List<CategoriaResponse>> listar() {
        return ApiResponse.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public ApiResponse<CategoriaResponse> buscarPorId(@PathVariable Long id) {
        return ApiResponse.ok(categoriaService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nueva categoría")
    public ApiResponse<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        return ApiResponse.ok("Categoría creada", categoriaService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public ApiResponse<CategoriaResponse> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody CategoriaRequest request) {
        return ApiResponse.ok("Categoría actualizada", categoriaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría (baja lógica)")
    public ApiResponse<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ApiResponse.ok("Categoría eliminada", null);
    }
}
