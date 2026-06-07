package com.restaurant.maestros.controller;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.maestros.dto.request.ClienteRequest;
import com.restaurant.maestros.dto.response.ClienteResponse;
import com.restaurant.maestros.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maestros/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gestión de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ApiResponse<List<ClienteResponse>> listar() {
        return ApiResponse.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ApiResponse<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ApiResponse.ok(clienteService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo cliente")
    public ApiResponse<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request) {
        return ApiResponse.ok("Cliente creado", clienteService.crear(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ClienteResponse> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody ClienteRequest request) {
        return ApiResponse.ok("Cliente actualizado", clienteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ApiResponse.ok("Cliente eliminado", null);
    }
}
