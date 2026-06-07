package com.restaurant.ventas.entity;

import com.restaurant.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DetallePedido extends BaseEntity {

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "producto_nombre", nullable = false, length = 150)
    private String productoNombre;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(length = 255)
    private String notas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
}

