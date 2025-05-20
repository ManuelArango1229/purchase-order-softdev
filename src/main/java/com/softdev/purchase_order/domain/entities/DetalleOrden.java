package com.softdev.purchase_order.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa un detalle de una orden de compra.
 */
@AllArgsConstructor
@Data
public class DetalleOrden {
    /**
     * Nombre del producto.
     */
    private String nombreProducto;
    /**
     * Cantidad de productos.
     */
    private int cantidad;
    /**
     * Precio unitario del producto.
     */
    private BigDecimal precioUnitario;
    /**
     * Subtotal del detalle de la orden (cantidad * precio unitario).
     */
    private BigDecimal subtotal;

    /**
     * Imprime la información del detalle de la orden en formato de cadena.
     *
     * @return Cadena que representa la información del detalle de la orden.
     */
    public String toString() {
        return "DetalleOrden{" +
                "nombreProducto='" + nombreProducto + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
