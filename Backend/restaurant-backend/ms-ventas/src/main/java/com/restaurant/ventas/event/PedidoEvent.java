package com.restaurant.ventas.event;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PedidoEvent implements Serializable {

    private Long pedidoId;
    private Long mesaId;
    private Integer mesaNumero;
    private String estado;
    private BigDecimal total;
    private String observaciones;
    private LocalDateTime fechaEvento;
}
