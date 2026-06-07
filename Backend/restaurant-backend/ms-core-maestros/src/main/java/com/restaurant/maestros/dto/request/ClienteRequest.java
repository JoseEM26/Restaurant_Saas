package com.restaurant.maestros.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellido;

    private String telefono;

    @Email(message = "Email inválido")
    private String email;
}
