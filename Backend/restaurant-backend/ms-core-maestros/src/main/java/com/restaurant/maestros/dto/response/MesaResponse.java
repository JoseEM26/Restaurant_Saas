package com.restaurant.maestros.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MesaResponse {
    private Long id;
    private Integer numero;
    private Integer capacidad;
    private String estado;
}
