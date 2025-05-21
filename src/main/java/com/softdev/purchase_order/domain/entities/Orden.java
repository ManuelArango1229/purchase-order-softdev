package com.softdev.purchase_order.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa una orden de compra.
 */
@AllArgsConstructor
@Data
public class Orden {
    /**
     * Identificador único de la orden.
     */
    private UUID id;
    /**
     * Correo electrónico del cliente.
     */
    private String emailCliente;
    /**
     * Nombre del cliente.
     */
    private String nombreCliente;
    /**
     * DNI del cliente.
     */
    private String dniCliente;
    /**
     * Lista de detalles de la orden.
     */
    private List<DetalleOrden> detalles;
    /**
     * Método de pago utilizado para la orden.
     */
    private MetodoPago metodoPago;
    /**
     * Valor total de la orden.
     */
    private BigDecimal valorTotal;
    /**
     * Fecha y hora en que se realizó el pedido.
     */
    private LocalDateTime fechaPedido;
    /**
     * Estado actual de la orden.
     */
    private EstadoOrden estado;

    /**
     * Imprime la información de la orden en formato de cadena.
     *
     * @return Cadena que representa la información de la orden.
     */
    public String toString() {
        return "Orden{"
                + "id=" + id
                + ", emailCliente='" + emailCliente + '\''
                + ", nombreCliente='" + nombreCliente + '\''
                + ", dniCliente='" + dniCliente + '\''
                + ", detalles=" + detalles
                + ", metodoPago=" + metodoPago
                + ", valorTotal=" + valorTotal
                + ", fechaPedido=" + fechaPedido
                + ", estado=" + estado
                + '}';
    }
}
