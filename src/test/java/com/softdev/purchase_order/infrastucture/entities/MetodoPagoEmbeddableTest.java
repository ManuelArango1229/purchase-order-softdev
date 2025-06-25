package com.softdev.purchase_order.infrastucture.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetodoPagoEmbeddableTest {

    @Test
    void testCrearMetodoPagoEmbeddable() {
        MetodoPagoEmbeddable metodoPago = new MetodoPagoEmbeddable();
        metodoPago.setNombre("VISA");
        metodoPago.setNumeroTarjeta("1234567812345678");
        metodoPago.setFechaExpiracion("12/30");
        metodoPago.setCvv("123");
        metodoPago.setNombreTitular("Juan Cliente");

        assertEquals("VISA", metodoPago.getNombre());
        assertEquals("1234567812345678", metodoPago.getNumeroTarjeta());
        assertEquals("12/30", metodoPago.getFechaExpiracion());
        assertEquals("123", metodoPago.getCvv());
        assertEquals("Juan Cliente", metodoPago.getNombreTitular());
    }
}
