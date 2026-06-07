package com.restaurant.maestros.entity;

import com.restaurant.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Cliente extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150, unique = true)
    private String email;
}

