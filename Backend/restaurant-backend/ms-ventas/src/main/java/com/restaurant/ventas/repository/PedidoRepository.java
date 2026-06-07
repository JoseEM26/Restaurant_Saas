package com.restaurant.ventas.repository;

import com.restaurant.ventas.entity.Pedido;
import com.restaurant.ventas.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByMesaId(Long mesaId);

    Optional<Pedido> findByMesaIdAndEstadoNot(Long mesaId, EstadoPedido estado);
}
