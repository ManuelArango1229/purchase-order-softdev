package com.softdev.purchase_order.infrastucture.entities;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DetalleOrdenEntityTest {

    @Test
    void testCrearDetalleOrdenEntity() {
        DetalleOrdenEntity detalle = new DetalleOrdenEntity();
        detalle.setNombreProducto("Producto Test");
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(10.5);
        detalle.setSubtotal(31.5);

        assertEquals("Producto Test", detalle.getNombreProducto());
        assertEquals(3, detalle.getCantidad());
        assertEquals(10.5, detalle.getPrecioUnitario());
        assertEquals(31.5, detalle.getSubtotal());
    }

    @Test
    void testGetOrdenIdCuandoOrdenNoEsNula() {
        OrdenEntity orden = new OrdenEntity();
        UUID ordenId = UUID.randomUUID();
        orden.setId(ordenId);

        DetalleOrdenEntity detalle = new DetalleOrdenEntity();
        detalle.setOrden(orden);

        assertEquals(ordenId.toString(), detalle.getOrdenId());
    }

    @Test
    void testGetOrdenIdCuandoOrdenEsNula() {
        DetalleOrdenEntity detalle = new DetalleOrdenEntity();
        assertNull(detalle.getOrdenId());
    }
}
