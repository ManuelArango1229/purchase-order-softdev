package com.softdev.purchase_order.domain.repositories;

import java.math.BigDecimal;

/**
 * Interfaz que define el caso de uso para verificar y actualizar el stock de productos.
 */
public interface ProductoServicePort {
    /**
     * Verifica si un producto existe en el sistema.
     *
     * @param nombreProducto El nombre del producto a verificar.
     * @return true si el producto existe, false en caso contrario.
     */
    boolean existeProducto(String nombreProducto);
    /**
     * Verifica si hay suficiente stock de un producto.
     *
     * @param nombreProducto El nombre del producto a verificar.
     * @param cantidad La cantidad a verificar.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    boolean verificarStock(String nombreProducto, int cantidad);
    /**
     * Actualiza el stock de un producto.
     *
     * @param nombreProducto El nombre del producto a actualizar.
     * @param cantidad La cantidad a restar del stock.
     */
    void actualizarStock(String nombreProducto, int cantidad);
    /**
     * Obtiene el precio de un producto.
     *
     * @param nombreProducto El nombre del producto.
     * @return El precio del producto.
     */
    BigDecimal obtenerPrecio(String nombreProducto);
}
