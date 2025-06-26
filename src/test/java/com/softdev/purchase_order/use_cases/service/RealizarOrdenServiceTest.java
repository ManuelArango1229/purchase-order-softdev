package com.softdev.purchase_order.use_cases.service;

import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.entities.MetodoPago;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.ProductoServicePort;
import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;
import com.softdev.purchase_order.infrastucture.messaging.OrdenPublisherService;
import com.softdev.purchase_order.use_cases.dto.response.UsuarioResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class RealizarOrdenServiceTest {

    private OrdenRepositoryPort ordenRepositoryPort;
    private ProductoServicePort productoServicePort;
    private UsuarioServicePort usuarioServicePort;
    private OrdenPublisherService ordenPublisherService;
    private RealizarOrdenService service;

    @BeforeEach
    void setUp() {
        ordenRepositoryPort = mock(OrdenRepositoryPort.class);
        productoServicePort = mock(ProductoServicePort.class);
        usuarioServicePort = mock(UsuarioServicePort.class);
        ordenPublisherService = mock(OrdenPublisherService.class);

        service = new RealizarOrdenService(
                ordenRepositoryPort,
                productoServicePort,
                usuarioServicePort,
                ordenPublisherService
        );
    }

    @Test
    void reenviarOrdenARabbitMQ_deberiaEjecutarseCorrectamente() {
        // Arrange: crear una orden válida y bien formada
        UUID ordenId = UUID.randomUUID();

        DetalleOrden detalle = new DetalleOrden(
                "ProductoTest",
                2,
                BigDecimal.valueOf(100),
                ordenId.toString()
        );

        MetodoPago metodoPago = new MetodoPago(
                "Tarjeta de crédito",
                "1234567890123456",
                "12/30",
                "123",
                "Cliente Prueba"
        );

        Orden orden = new Orden(
                ordenId,
                "cliente@correo.com",
                "Cliente Prueba",
                "12345678",
                "Dirección Prueba",
                List.of(detalle),
                metodoPago,
                BigDecimal.valueOf(200),
                LocalDateTime.now(),
                com.softdev.purchase_order.domain.entities.EstadoOrden.CREADA
        );

        // Act
        service.reenviarOrdenARabbitMQ(orden);

        // Assert
        verify(ordenPublisherService, times(1))
                .publicarOrdenConMensaje(any(), eq("Reenvío de orden"));
    }
}
