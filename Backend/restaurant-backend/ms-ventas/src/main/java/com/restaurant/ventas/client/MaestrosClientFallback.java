package com.restaurant.ventas.client;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.ventas.client.dto.MesaClientResponse;
import com.restaurant.ventas.client.dto.ProductoClientResponse;
import org.springframework.stereotype.Component;

// Fallback Resilience4j: respuesta por defecto si ms-core-maestros no responde
@Component
public class MaestrosClientFallback implements MaestrosClient {

    @Override
    public ApiResponse<ProductoClientResponse> obtenerProducto(Long id) {
        return ApiResponse.error("Servicio de maestros no disponible");
    }

    @Override
    public ApiResponse<MesaClientResponse> obtenerMesa(Long id) {
        return ApiResponse.error("Servicio de maestros no disponible");
    }

    @Override
    public ApiResponse<MesaClientResponse> cambiarEstadoMesa(Long id, String estado) {
        return ApiResponse.error("Servicio de maestros no disponible");
    }
}
