package com.softdev.purchase_order.infrastucture.rest;

import com.softdev.purchase_order.domain.entities.Orden;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;
import com.softdev.purchase_order.infrastucture.security.JwtUtils;
import com.softdev.purchase_order.use_cases.dto.request.MetodoPagoRequest;
import com.softdev.purchase_order.use_cases.dto.request.ProductoOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.request.RealizarOrdenRequest;
import com.softdev.purchase_order.use_cases.dto.response.OrdenConDetallesDTO;
import com.softdev.purchase_order.use_cases.service.ObtenerOrdenConDetallesService;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OrdenControllerTest {

    private OrdenController controller;
    private RealizarOrdenPort realizarOrdenPort;
    private ObtenerOrdenConDetallesService obtenerOrdenConDetallesService;
    private MockedStatic<JwtUtils> jwtUtilsMock;

    @BeforeEach
    void setUp() {
        jwtUtilsMock = mockStatic(JwtUtils.class);
        jwtUtilsMock.when(() -> JwtUtils.getClaim("sub")).thenReturn("cliente@email.com");

        realizarOrdenPort = mock(RealizarOrdenPort.class);
        obtenerOrdenConDetallesService = mock(ObtenerOrdenConDetallesService.class);

        controller = new OrdenController(realizarOrdenPort, obtenerOrdenConDetallesService);
    }

    @AfterEach
    void tearDown() {
        jwtUtilsMock.close();
    }

    @Test
    void realizarOrden_DeberiaCrearOrden() {
        // Arrange
        RealizarOrdenRequest request = crearRequestValido();
        Orden orden = crearOrdenEjemplo();

        when(realizarOrdenPort.realizarOrden(any(), anyString())).thenReturn(orden);

        // Act
        var response = controller.realizarOrden(request, "Bearer token");

        // Assert
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void realizarOrden_ConProductosVacios_DeberiaRetornarBadRequest() {
        // Arrange
        RealizarOrdenRequest request = new RealizarOrdenRequest(List.of(), crearMetodoPagoValido());

        // Act
        var response = controller.realizarOrden(request, "Bearer token");

        // Assert
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("La orden debe contener al menos un producto"));
    }

    @Test
    void realizarOrden_ErrorInterno_DeberiaRetornar500() {
        // Arrange
        RealizarOrdenRequest request = crearRequestValido();

        when(realizarOrdenPort.realizarOrden(any(), anyString()))
                .thenThrow(new RuntimeException("Fallo interno"));

        // Act
        var response = controller.realizarOrden(request, "Bearer token");

        // Assert
        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("Error interno"));
    }

    @Test
    void obtenerFactura_DeberiaRetornarFactura() {
        // Arrange
        UUID id = UUID.randomUUID();
        OrdenConDetallesDTO dto = new OrdenConDetallesDTO();
        dto.setId(id.toString());

        when(obtenerOrdenConDetallesService.ejecutar(id)).thenReturn(Optional.of(dto));

        // Act
        var response = controller.obtenerFactura(id.toString());

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void obtenerFactura_IdInvalido_DeberiaRetornarBadRequest() {
        // Act
        var response = controller.obtenerFactura("id-invalido");

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void obtenerFactura_NoEncontrada_DeberiaRetornarNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(obtenerOrdenConDetallesService.ejecutar(id)).thenReturn(Optional.empty());

        // Act
        var response = controller.obtenerFactura(id.toString());

        // Assert
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
void realizarOrden_TokenSinEmail_DeberiaRetornarBadRequest() {
    // Arrange
    jwtUtilsMock.when(() -> JwtUtils.getClaim("sub")).thenReturn(null);
    RealizarOrdenRequest request = crearRequestValido();

    // Act
    var response = controller.realizarOrden(request, "Bearer token");

    // Assert
    assertEquals(400, response.getStatusCode().value());
    assertTrue(response.getBody().toString().contains("El token no contiene un correo válido"));
}

@Test
void realizarOrden_CantidadProductoInvalida_DeberiaRetornarBadRequest() {
    // Arrange
    RealizarOrdenRequest request = new RealizarOrdenRequest(
            List.of(new ProductoOrdenRequest("producto1", 0)),
            crearMetodoPagoValido()
    );

    // Act
    var response = controller.realizarOrden(request, "Bearer token");

    // Assert
    assertEquals(400, response.getStatusCode().value());
    assertTrue(response.getBody().toString().contains("La cantidad de producto debe ser mayor a 0"));
}

@Test
void realizarOrden_MetodoPagoInvalido_DeberiaRetornarBadRequest() {
    // Arrange
    MetodoPagoRequest metodoPagoInvalido = new MetodoPagoRequest(
            "VISA",
            "123", // Número inválido (debe tener 16 dígitos)
            "12/30",
            "123",
            "Juan Cliente"
    );
    RealizarOrdenRequest request = new RealizarOrdenRequest(
            List.of(new ProductoOrdenRequest("producto1", 2)),
            metodoPagoInvalido
    );

    // Act
    var response = controller.realizarOrden(request, "Bearer token");

    // Assert
    assertEquals(400, response.getStatusCode().value());
    assertTrue(response.getBody().toString().contains("El número de tarjeta debe tener 16 dígitos"));
}


    // 🔧 Helpers para datos de prueba
    private RealizarOrdenRequest crearRequestValido() {
        return new RealizarOrdenRequest(
                List.of(new ProductoOrdenRequest("producto1", 2)),
                crearMetodoPagoValido()
        );
    }

    private MetodoPagoRequest crearMetodoPagoValido() {
        return new MetodoPagoRequest(
                "VISA",
                "1234567812345678",
                "12/30",
                "123",
                "Juan Cliente"
        );
    }

    private Orden crearOrdenEjemplo() {
        return new Orden(
                UUID.randomUUID(),
                "cliente@email.com",
                "Juan Cliente",
                "12345678A",
                "Calle Falsa 123",
                List.of(),
                new com.softdev.purchase_order.domain.entities.MetodoPago("VISA", "1234567812345678", "12/30", "123", "Juan Cliente"),
                BigDecimal.valueOf(100.0),
                LocalDateTime.now(),
                com.softdev.purchase_order.domain.entities.EstadoOrden.CREADA
        );
    }
}
