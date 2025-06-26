package com.softdev.purchase_order.use_cases.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoOrdenRequestTest {

    @Test
    void testConstructorYGetters() {
        ProductoOrdenRequest producto = new ProductoOrdenRequest("Pan", 5);

        assertEquals("Pan", producto.getProducto());
        assertEquals(5, producto.getCantidad());
    }

    @Test
    void testConstructorVacio() {
        ProductoOrdenRequest producto = new ProductoOrdenRequest();
        assertNotNull(producto);
    }
}
