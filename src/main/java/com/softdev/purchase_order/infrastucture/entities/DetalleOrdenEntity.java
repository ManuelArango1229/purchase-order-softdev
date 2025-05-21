package com.softdev.purchase_order.infrastucture.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un detalle de una orden de compra.
 */
@Entity
@Table(name = "detalles_orden")
@Data
@NoArgsConstructor
public class DetalleOrdenEntity {
    /**
     * Identificador único del detalle de la orden.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private double precioUnitario;
    /**
     * Subtotal del detalle de la orden (cantidad * precio unitario).
     */
    private double subtotal;
}
