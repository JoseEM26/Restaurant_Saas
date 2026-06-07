package com.restaurant.maestros.entity;

import com.restaurant.common.entity.BaseEntity;
import com.restaurant.maestros.enums.EstadoMesa;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "mesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Mesa extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoMesa estado = EstadoMesa.LIBRE;
}


