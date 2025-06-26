package com.softdev.purchase_order.use_cases.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetodoPagoRequestTest {

    @Test
    void testConstructorYGetters() {
        MetodoPagoRequest metodo = new MetodoPagoRequest(
                "VISA", "1234567812345678", "12/30", "123", "Juan Cliente"
        );

        assertEquals("VISA", metodo.getMetodoPago());
        assertEquals("1234567812345678", metodo.getNumeroTarjeta());
        assertEquals("12/30", metodo.getFechaExpiracion());
        assertEquals("123", metodo.getCvv());
        assertEquals("Juan Cliente", metodo.getNombreTitular());
    }

    @Test
    void testConstructorVacio() {
        MetodoPagoRequest metodo = new MetodoPagoRequest();
        assertNotNull(metodo);
    }
}
