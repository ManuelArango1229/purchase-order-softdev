package com.softdev.purchase_order.use_cases.service;

import com.softdev.purchase_order.domain.entities.DetalleOrden;
import com.softdev.purchase_order.domain.entities.MetodoPago;
import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.entities.EstadoOrden;
import com.softdev.purchase_order.domain.repositories.DetalleOrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.use_cases.dto.response.OrdenConDetallesDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObtenerOrdenConDetallesServiceTest {

    private OrdenRepositoryPort ordenRepositoryPort;
    private DetalleOrdenRepositoryPort detalleOrdenRepositoryPort;
    private ObtenerOrdenConDetallesService service;

    @BeforeEach
    void setUp() {
        ordenRepositoryPort = mock(OrdenRepositoryPort.class);
        detalleOrdenRepositoryPort = mock(DetalleOrdenRepositoryPort.class);

        service = new ObtenerOrdenConDetallesService(
                ordenRepositoryPort,
                detalleOrdenRepositoryPort
        );
    }

    @Test
    void ejecutar_deberiaRetornarOrdenConDetallesDTO_SiExiste() {
        // Arrange
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
                EstadoOrden.CREADA
        );

        when(ordenRepositoryPort.findById(ordenId)).thenReturn(Optional.of(orden));
        when(detalleOrdenRepositoryPort.findByOrdenId(ordenId.toString())).thenReturn(List.of(detalle));

        // Act
        Optional<OrdenConDetallesDTO> result = service.ejecutar(ordenId);

        // Assert
        assertTrue(result.isPresent());
        OrdenConDetallesDTO dto = result.get();
        assertEquals(ordenId.toString(), dto.getId());
        assertEquals("cliente@correo.com", dto.getEmailCliente());
        assertEquals("Cliente Prueba", dto.getNombreCliente());
        assertEquals("12345678", dto.getDniCliente());
        assertEquals("Dirección Prueba", dto.getDireccion());
        assertEquals(1, dto.getDetalles().size());
        assertEquals("ProductoTest", dto.getDetalles().get(0).getNombreProducto());
        assertEquals("Tarjeta de crédito", dto.getMetodoPago().getNombre());

        verify(ordenRepositoryPort).findById(ordenId);
        verify(detalleOrdenRepositoryPort).findByOrdenId(ordenId.toString());
    }

    @Test
    void ejecutar_deberiaRetornarEmpty_SiNoExisteLaOrden() {
        // Arrange
        UUID ordenId = UUID.randomUUID();
        when(ordenRepositoryPort.findById(ordenId)).thenReturn(Optional.empty());

        // Act
        Optional<OrdenConDetallesDTO> result = service.ejecutar(ordenId);

        // Assert
        assertTrue(result.isEmpty());
        verify(ordenRepositoryPort).findById(ordenId);
        verifyNoInteractions(detalleOrdenRepositoryPort);
    }
}
