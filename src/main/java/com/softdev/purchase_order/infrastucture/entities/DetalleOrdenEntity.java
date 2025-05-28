package com.softdev.purchase_order.infrastucture.entities;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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

    /**
     * Relación con la orden a la que pertenece este detalle.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Relación con la orden
    @JoinColumn(name = "ordenId") // Asegúrate que coincida con el nombre en tu tabla detalles_orden
    private OrdenEntity orden;

    /**
     * Retorna una representación en cadena del detalle de la orden.
     * @return Cadena que representa la información del detalle de la orden.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retorna el ID de la orden a la que pertenece este detalle.
     * @return ID de la orden como cadena, o null si la orden es null.
     */
    public String getOrdenId() {
        return (orden != null) ? orden.getId().toString() : null;
    }
}
