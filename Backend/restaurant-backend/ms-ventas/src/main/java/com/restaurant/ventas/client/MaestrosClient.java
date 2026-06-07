package com.restaurant.ventas.client;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.ventas.client.dto.ProductoClientResponse;
import com.restaurant.ventas.client.dto.MesaClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

// name = nombre del servicio en Eureka
@FeignClient(name = "ms-core-maestros", fallback = MaestrosClientFallback.class)
public interface MaestrosClient {

    @GetMapping("/api/maestros/productos/{id}")
    ApiResponse<ProductoClientResponse> obtenerProducto(@PathVariable Long id);

    @GetMapping("/api/maestros/mesas/{id}")
    ApiResponse<MesaClientResponse> obtenerMesa(@PathVariable Long id);

    @PatchMapping("/api/maestros/mesas/{id}/estado")
    ApiResponse<MesaClientResponse> cambiarEstadoMesa(@PathVariable Long id,
                                                       @RequestParam String estado);
}
