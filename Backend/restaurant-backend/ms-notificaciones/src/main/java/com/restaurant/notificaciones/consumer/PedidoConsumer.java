package com.restaurant.notificaciones.consumer;

import com.restaurant.notificaciones.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class PedidoConsumer {

    // Consume el evento cuando se crea un pedido nuevo
    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDO_NUEVO)
    public void recibirPedidoNuevo(Map<String, Object> evento) {
        log.info("=== NUEVO PEDIDO RECIBIDO ===");
        log.info("Pedido ID : {}", evento.get("pedidoId"));
        log.info("Mesa ID   : {}", evento.get("mesaId"));
        log.info("Total     : S/. {}", evento.get("total"));
        log.info("Estado    : {}", evento.get("estado"));

        // Aquí puedes: enviar email, push notification, notificar cocina, etc.
        procesarNotificacion(evento, "NUEVO PEDIDO");
    }

    // Consume el evento cuando se actualiza un pedido
    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDO_ACTUALIZADO)
    public void recibirPedidoActualizado(Map<String, Object> evento) {
        log.info("=== PEDIDO ACTUALIZADO ===");
        log.info("Pedido ID : {}", evento.get("pedidoId"));
        log.info("Nuevo estado: {}", evento.get("estado"));
        log.info("Total     : S/. {}", evento.get("total"));

        procesarNotificacion(evento, "PEDIDO ACTUALIZADO");
    }

    private void procesarNotificacion(Map<String, Object> evento, String tipo) {
        // Extensible: en un caso real aquí iría la lógica de envío
        log.info("[{}] Notificación procesada para pedido: {}", tipo, evento.get("pedidoId"));
    }
}
