package com.restaurant.ventas.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoClientResponse {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Boolean disponible;
}
