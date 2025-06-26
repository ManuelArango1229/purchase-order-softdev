package com.softdev.purchase_order.infrastucture.messaging;

import com.softdev.purchase_order.use_cases.dto.response.OrdenConDetallesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrdenPublisherServiceTest {

    private AmqpTemplate amqpTemplate;
    private OrdenPublisherService publisherService;

    @BeforeEach
    void setUp() {
        amqpTemplate = mock(AmqpTemplate.class);
        publisherService = new OrdenPublisherService(amqpTemplate);
    }

    @Test
    void testPublicarOrden_DeberiaEnviarMensaje() {
        // Arrange
        OrdenConDetallesDTO orden = new OrdenConDetallesDTO();
        orden.setId("1234");
        orden.setDireccion("direccion-prueba");

        // Act
        assertDoesNotThrow(() -> publisherService.publicarOrden(orden));

        // Assert
        verify(amqpTemplate, times(1)).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.ROUTING_KEY),
                eq(orden)
        );
    }

    @Test
    void testPublicarOrden_DeberiaLanzarExcepcionSiFalla() {
        // Arrange
        OrdenConDetallesDTO orden = new OrdenConDetallesDTO();
        orden.setId("fail-123");
        orden.setDireccion("direccion-error");

        doThrow(new RuntimeException("RabbitMQ error"))
        .when(amqpTemplate).convertAndSend(
        any(String.class), 
        any(String.class), 
        any(Object.class)
    );


        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> publisherService.publicarOrden(orden));

        assertEquals("Error al publicar la orden en RabbitMQ", exception.getMessage());
    }

@Test
void testPublicarOrdenConMensaje_DeberiaEnviarMensajeConWrapper() {
    // Arrange
    OrdenConDetallesDTO orden = new OrdenConDetallesDTO();
    orden.setId("5678");
    orden.setDireccion("direccion-msg");
    String mensaje = "Mensaje adicional";

    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

    // Act
    assertDoesNotThrow(() -> publisherService.publicarOrdenConMensaje(orden, mensaje));

    // Assert
    verify(amqpTemplate).convertAndSend(
            eq(RabbitMQConfig.EXCHANGE_NAME),
            eq(RabbitMQConfig.ROUTING_KEY),
            captor.capture()
    );

    Object sentObject = captor.getValue();
    assertNotNull(sentObject);
    assertTrue(sentObject instanceof OrdenPublisherService.OrdenMensajeWrapper);

    // 🔥 Accedemos a los atributos privados mediante reflexión
    OrdenPublisherService.OrdenMensajeWrapper wrapper =
            (OrdenPublisherService.OrdenMensajeWrapper) sentObject;

    try {
        var ordenField = OrdenPublisherService.OrdenMensajeWrapper.class.getDeclaredField("orden");
        ordenField.setAccessible(true);
        var mensajeField = OrdenPublisherService.OrdenMensajeWrapper.class.getDeclaredField("mensaje");
        mensajeField.setAccessible(true);

        OrdenConDetallesDTO ordenEnviado = (OrdenConDetallesDTO) ordenField.get(wrapper);
        String mensajeEnviado = (String) mensajeField.get(wrapper);

        assertNotNull(ordenEnviado);
        assertEquals(orden.getId(), ordenEnviado.getId(), "El ID debe coincidir");
        assertEquals(mensaje, mensajeEnviado, "El mensaje adicional debe coincidir");

    } catch (NoSuchFieldException | IllegalAccessException e) {
        fail("Error accediendo a los campos del wrapper: " + e.getMessage());
    }
}



    @Test
    void testPublicarOrdenConMensaje_DeberiaLanzarExcepcionSiFalla() {
        // Arrange
        OrdenConDetallesDTO orden = new OrdenConDetallesDTO();
        orden.setId("error-msg");
        orden.setDireccion("direccion-msg");

        doThrow(new RuntimeException("RabbitMQ error"))
        .when(amqpTemplate).convertAndSend(
        any(String.class), 
        any(String.class), 
        any(Object.class)
    );


        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> publisherService.publicarOrdenConMensaje(orden, "mensaje-error"));

        assertEquals("Error al publicar la orden en RabbitMQ", exception.getMessage());
    }
}
