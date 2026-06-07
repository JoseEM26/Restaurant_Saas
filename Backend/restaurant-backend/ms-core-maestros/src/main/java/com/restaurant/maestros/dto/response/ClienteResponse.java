package com.restaurant.maestros.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
}
