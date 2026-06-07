package com.restaurant.ventas.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequest {

    @NotNull(message = "La mesa es obligatoria")
    private Long mesaId;

    private Long clienteId;

    private String observaciones;

    @NotEmpty(message = "El pedido debe tener al menos un producto")
    @Valid
    private List<DetallePedidoRequest> detalles;
}
