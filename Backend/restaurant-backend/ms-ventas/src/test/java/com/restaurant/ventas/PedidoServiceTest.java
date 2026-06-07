package com.restaurant.ventas;

import com.restaurant.common.dto.ApiResponse;
import com.restaurant.common.exception.BusinessException;
import com.restaurant.ventas.client.MaestrosClient;
import com.restaurant.ventas.client.dto.MesaClientResponse;
import com.restaurant.ventas.client.dto.ProductoClientResponse;
import com.restaurant.ventas.config.RabbitMQConfig;
import com.restaurant.ventas.dto.request.DetallePedidoRequest;
import com.restaurant.ventas.dto.request.PedidoRequest;
import com.restaurant.ventas.mapper.PedidoMapper;
import com.restaurant.ventas.repository.PedidoRepository;
import com.restaurant.ventas.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService - Tests unitarios")
class PedidoServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @Mock private PedidoMapper pedidoMapper;
    @Mock private MaestrosClient maestrosClient;
    @Mock private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoRequest pedidoRequest;
    private MesaClientResponse mesaOcupada;
    private MesaClientResponse mesaLibre;
    private ProductoClientResponse producto;

    @BeforeEach
    void setUp() {
        DetallePedidoRequest detalle = new DetallePedidoRequest();
        detalle.setProductoId(1L);
        detalle.setCantidad(2);

        pedidoRequest = new PedidoRequest();
        pedidoRequest.setMesaId(1L);
        pedidoRequest.setDetalles(List.of(detalle));

        mesaOcupada = new MesaClientResponse();
        mesaOcupada.setId(1L);
        mesaOcupada.setNumero(1);
        mesaOcupada.setEstado("OCUPADA");

        mesaLibre = new MesaClientResponse();
        mesaLibre.setId(1L);
        mesaLibre.setNumero(1);
        mesaLibre.setEstado("LIBRE");

        producto = new ProductoClientResponse();
        producto.setId(1L);
        producto.setNombre("Ceviche");
        producto.setPrecio(BigDecimal.valueOf(35.00));
        producto.setDisponible(true);
    }

    @Test
    @DisplayName("Crear pedido falla si la mesa está ocupada")
    void crear_mesaOcupada_lanzaExcepcion() {
        when(maestrosClient.obtenerMesa(1L))
                .thenReturn(ApiResponse.ok(mesaOcupada));

        assertThatThrownBy(() -> pedidoService.crear(pedidoRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("no está disponible");

        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Crear pedido falla si el servicio de maestros no responde")
    void crear_maestrosNoDisponible_lanzaExcepcion() {
        when(maestrosClient.obtenerMesa(1L))
                .thenReturn(ApiResponse.error("Servicio no disponible"));

        assertThatThrownBy(() -> pedidoService.crear(pedidoRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("no encontrada");
    }
}
