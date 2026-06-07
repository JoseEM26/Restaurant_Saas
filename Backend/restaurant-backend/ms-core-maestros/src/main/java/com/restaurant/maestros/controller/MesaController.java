package com.restaurant.maestros.controller;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.maestros.dto.request.MesaRequest;
import com.restaurant.maestros.dto.response.MesaResponse;
import com.restaurant.maestros.enums.EstadoMesa;
import com.restaurant.maestros.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maestros/mesas")
@RequiredArgsConstructor
@Tag(name = "Mesas", description = "Gestión de mesas del restaurante")
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    @Operation(summary = "Listar todas las mesas")
    public ApiResponse<List<MesaResponse>> listar() {
        return ApiResponse.ok(mesaService.listarTodas());
    }

    @GetMapping("/libres")
    @Operation(summary = "Listar mesas libres")
    public ApiResponse<List<MesaResponse>> listarLibres() {
        return ApiResponse.ok(mesaService.listarLibres());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mesa por ID")
    public ApiResponse<MesaResponse> buscarPorId(@PathVariable Long id) {
        return ApiResponse.ok(mesaService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nueva mesa")
    public ApiResponse<MesaResponse> crear(@Valid @RequestBody MesaRequest request) {
        return ApiResponse.ok("Mesa creada", mesaService.crear(request));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de la mesa")
    public ApiResponse<MesaResponse> cambiarEstado(@PathVariable Long id,
                                                    @RequestParam EstadoMesa estado) {
        return ApiResponse.ok("Estado actualizado", mesaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mesa (baja lógica)")
    public ApiResponse<Void> eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return ApiResponse.ok("Mesa eliminada", null);
    }
}
