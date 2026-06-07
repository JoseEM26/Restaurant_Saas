package com.restaurant.ventas.entity;

import com.restaurant.common.entity.BaseEntity;
import com.restaurant.ventas.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Pedido extends BaseEntity {

    @Column(name = "mesa_id", nullable = false)
    private Long mesaId;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.ABIERTO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Column(length = 255)
    private String observaciones;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    public void recalcularTotal() {
        this.total = detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}


