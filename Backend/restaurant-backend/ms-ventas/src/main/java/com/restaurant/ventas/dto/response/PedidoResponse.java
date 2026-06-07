package com.restaurant.ventas.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PedidoResponse {
    private Long id;
    private Long mesaId;
    private Long clienteId;
    private String estado;
    private BigDecimal total;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private List<DetallePedidoResponse> detalles;
}
