package com.restaurant.maestros.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Boolean disponible;
    private Long categoriaId;
    private String categoriaNombre;
}
