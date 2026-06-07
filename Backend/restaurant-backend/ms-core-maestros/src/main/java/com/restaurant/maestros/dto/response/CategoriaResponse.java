package com.restaurant.maestros.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
