package com.softdev.purchase_order.use_cases.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa un producto en una orden de compra.
 */
@AllArgsConstructor
@Getter
public class ProductoOrdenRequest {
    /**
     * Nombre del producto.
     */
    private String producto;
    /**
     * Cantidad de productos.
     */
    private int cantidad;

    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public ProductoOrdenRequest() {
    }
}
