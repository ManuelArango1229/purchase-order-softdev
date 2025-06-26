package com.softdev.purchase_order.use_cases.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class RealizarOrdenRequestTest {

    @Test
    void testConstructorYGetters() {
        ProductoOrdenRequest producto = new ProductoOrdenRequest("Pan", 3);
        MetodoPagoRequest metodo = new MetodoPagoRequest(
                "VISA", "1234567812345678", "12/30", "123", "Juan Cliente"
        );

        RealizarOrdenRequest request = new RealizarOrdenRequest(
                List.of(producto), metodo
        );

        assertEquals(1, request.getProductos().size());
        assertEquals("Pan", request.getProductos().get(0).getProducto());
        assertEquals(3, request.getProductos().get(0).getCantidad());

        assertEquals("VISA", request.getMetodoPago().getMetodoPago());
        assertEquals("Juan Cliente", request.getMetodoPago().getNombreTitular());
    }

    @Test
    void testConstructorVacio() {
        RealizarOrdenRequest request = new RealizarOrdenRequest();
        assertNotNull(request);
    }
}
