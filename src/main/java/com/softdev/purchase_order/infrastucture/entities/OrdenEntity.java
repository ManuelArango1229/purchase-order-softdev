package com.softdev.purchase_order.infrastucture.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clase que representa una orden de compra.
 */
@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
public class OrdenEntity {
    /**
     * Identificador único de la orden.
     */
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    /**
     * Correo electrónico del cliente.
     */
    @Column(name = "email_cliente")
    private String emailCliente;
    /**
     * Nombre del cliente.
     */
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    /**
     * DNI del cliente.
     */
    @Column(name = "dni_cliente")
    private String dniCliente;
    /**
     * Valor total de la orden.
     */
    @Column(name = "valor_total")
    private Double valorTotal;
    /**
     * Fecha y hora en que se realizó el pedido.
     */
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;
    /**
     * Estado actual de la orden.
     */
    @Column(name = "estado")
    private String estado;
    /**
     * Método de pago utilizado para la orden.
     */
    @Embedded
    private MetodoPagoEmbeddable metodoPago;
    /**
     * Lista de detalles de la orden.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id") // Campo FK en DetalleOrdenEntity
    private List<DetalleOrdenEntity> detalles = new ArrayList<>();

    /**
     * Método que se ejecuta antes de persistir la entidad.
     * Genera un UUID único para la orden si no se ha asignado uno.
     */
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
    /**
     * Método que agrega un detalle a la orden.
     *
     * @param detalle Detalle de la orden a agregar.
     */
    public void addDetalle(final DetalleOrdenEntity detalle) {
        this.detalles.add(detalle);
    }
}
