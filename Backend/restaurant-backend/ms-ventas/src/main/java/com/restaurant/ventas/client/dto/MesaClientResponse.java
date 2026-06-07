package com.restaurant.ventas.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MesaClientResponse {
    private Long id;
    private Integer numero;
    private Integer capacidad;
    private String estado;
}
