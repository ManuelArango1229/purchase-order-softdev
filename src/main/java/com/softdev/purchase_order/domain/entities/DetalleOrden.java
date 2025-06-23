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
     * Identificador único del detalle de la orden.
     */
    private String ordenId;

    /**
     * Constructor que inicializa el detalle de la orden con el nombre del producto, cantidad y precio unitario.
     *
     * @param nombreProductoParam Nombre del producto.
     * @param cantidadParam       Cantidad de productos.
     * @param precioUnitarioParam Precio unitario del producto.
     * @param ordenIdParam Identificador único de la orden a la que pertenece este detalle.
     */
    public DetalleOrden(final String nombreProductoParam, final int cantidadParam, final BigDecimal precioUnitarioParam, final String ordenIdParam) {
        this.nombreProducto = nombreProductoParam;
        this.cantidad = cantidadParam;
        this.precioUnitario = precioUnitarioParam;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        this.ordenId = ordenIdParam;
    }

    /**
     * Constructor para crear un DetalleOrden sin ordenId (opcionalmente puede usarse en procesos donde aún no se ha generado la orden).
     *
     * @param nombreProductoParam Nombre del producto.
     * @param cantidadParam Cantidad de productos.
     * @param precioUnitarioParam Precio unitario del producto.
     */
    public DetalleOrden(final String nombreProductoParam, final int cantidadParam, final BigDecimal precioUnitarioParam) {
        this.nombreProducto = nombreProductoParam;
        this.cantidad = cantidadParam;
        this.precioUnitario = precioUnitarioParam;
        this.subtotal = precioUnitarioParam.multiply(BigDecimal.valueOf(cantidadParam));
        this.ordenId = null; // Si no hay orden asociada, se deja null o vacío.
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
