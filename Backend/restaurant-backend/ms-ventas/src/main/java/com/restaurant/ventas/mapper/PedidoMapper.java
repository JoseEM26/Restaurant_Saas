package com.restaurant.ventas.mapper;

import com.restaurant.ventas.dto.response.DetallePedidoResponse;
import com.restaurant.ventas.dto.response.PedidoResponse;
import com.restaurant.ventas.entity.DetallePedido;
import com.restaurant.ventas.entity.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {
        if (pedido == null) return null;
        return PedidoResponse.builder()
                .id(pedido.getId())
                .mesaId(pedido.getMesaId())
                .clienteId(pedido.getClienteId())
                .estado(pedido.getEstado().name())
                .total(pedido.getTotal())
                .observaciones(pedido.getObservaciones())
                .fechaCreacion(pedido.getCreatedAt())
                .detalles(pedido.getDetalles().stream()
                        .map(this::toDetalleResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public DetallePedidoResponse toDetalleResponse(DetallePedido d) {
        if (d == null) return null;
        return DetallePedidoResponse.builder()
                .id(d.getId())
                .productoId(d.getProductoId())
                .productoNombre(d.getProductoNombre())
                .cantidad(d.getCantidad())
                .precioUnitario(d.getPrecioUnitario())
                .subtotal(d.getSubtotal())
                .notas(d.getNotas())
                .build();
    }

    public List<PedidoResponse> toResponseList(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
