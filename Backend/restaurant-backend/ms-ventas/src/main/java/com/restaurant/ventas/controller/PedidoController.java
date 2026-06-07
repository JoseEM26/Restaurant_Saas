package com.restaurant.ventas.controller;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.ventas.dto.request.PedidoRequest;
import com.restaurant.ventas.dto.response.PedidoResponse;
import com.restaurant.ventas.enums.EstadoPedido;
import com.restaurant.ventas.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión de pedidos del restaurante")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos")
    public ApiResponse<List<PedidoResponse>> listar() {
        return ApiResponse.ok(pedidoService.listarTodos());
    }

    @GetMapping("/abiertos")
    @Operation(summary = "Listar pedidos abiertos")
    public ApiResponse<List<PedidoResponse>> listarAbiertos() {
        return ApiResponse.ok(pedidoService.listarAbiertos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    public ApiResponse<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ApiResponse.ok(pedidoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo pedido (verifica mesa via Feign y publica en RabbitMQ)")
    public ApiResponse<PedidoResponse> crear(@Valid @RequestBody PedidoRequest request) {
        return ApiResponse.ok("Pedido creado", pedidoService.crear(request));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado del pedido")
    public ApiResponse<PedidoResponse> cambiarEstado(@PathVariable Long id,
                                                      @RequestParam EstadoPedido estado) {
        return ApiResponse.ok("Estado actualizado", pedidoService.cambiarEstado(id, estado));
    }
}
