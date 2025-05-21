package com.softdev.purchase_order.domain.entities;

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
     * Constructor que inicializa el detalle de la orden con el nombre del producto, cantidad y precio unitario.
     *
     * @param nombreProducto Nombre del producto.
     * @param cantidad       Cantidad de productos.
     * @param precioUnitario Precio unitario del producto.
     */
    public DetalleOrden(final String nombreProducto, final int cantidad, final BigDecimal precioUnitario) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    /**
     * Imprime la información del detalle de la orden en formato de cadena.
     *
     * @return Cadena que representa la información del detalle de la orden.
     */
    public String toString() {
        return "DetalleOrden{"
                + "nombreProducto='" + nombreProducto + '\''
                + ", cantidad=" + cantidad
                + ", precioUnitario=" + precioUnitario
                + ", subtotal=" + subtotal
                + '}';
    }
}
