package com.softdev.purchase_order.infrastucture.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrdenEntityTest {

    @Test
    void testCrearOrdenEntity() {
        OrdenEntity orden = new OrdenEntity();
        UUID id = UUID.randomUUID();
        orden.setId(id);
        orden.setEmailCliente("cliente@email.com");
        orden.setNombreCliente("Juan Cliente");
        orden.setDniCliente("12345678A");
        orden.setDireccion("Calle Falsa 123");
        orden.setValorTotal(150.5);
        orden.setFechaPedido(LocalDateTime.now());
        orden.setEstado("CREADA");

        MetodoPagoEmbeddable metodoPago = new MetodoPagoEmbeddable();
        metodoPago.setNombre("VISA");
        orden.setMetodoPago(metodoPago);

        assertEquals(id, orden.getId());
        assertEquals("cliente@email.com", orden.getEmailCliente());
        assertEquals("Juan Cliente", orden.getNombreCliente());
        assertEquals("12345678A", orden.getDniCliente());
        assertEquals("Calle Falsa 123", orden.getDireccion());
        assertEquals(150.5, orden.getValorTotal());
        assertEquals("CREADA", orden.getEstado());
        assertEquals(metodoPago, orden.getMetodoPago());
    }

    @Test
    void testPrePersistGeneraIdSiEsNulo() {
        OrdenEntity orden = new OrdenEntity();
        assertNull(orden.getId());

        orden.prePersist();

        assertNotNull(orden.getId());
    }

    @Test
    void testAddDetalle() {
        OrdenEntity orden = new OrdenEntity();
        DetalleOrdenEntity detalle = new DetalleOrdenEntity();
        detalle.setNombreProducto("Producto Test");

        orden.addDetalle(detalle);

        assertEquals(1, orden.getDetalles().size());
        assertEquals("Producto Test", orden.getDetalles().get(0).getNombreProducto());
    }
}
