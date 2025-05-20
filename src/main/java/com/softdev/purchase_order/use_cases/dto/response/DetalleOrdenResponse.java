package com.softdev.purchase_order.use_cases.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa un detalle de una orden de compra.
 */
@AllArgsConstructor
@Getter
public class DetalleOrdenResponse {
    /**
     * Nombre del producto.
     */
    private String producto;
    /**
     * Precio unitario del producto.
     */
    private BigDecimal precio;
    /**
     * Cantidad de productos.
     */
    private int cantidad;
    /**
     * Subtotal del detalle de la orden (cantidad * precio unitario).
     */
    private BigDecimal subtotal;

    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public DetalleOrdenResponse() {
    }
}