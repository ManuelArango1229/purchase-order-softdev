package com.softdev.purchase_order.domain;

/**
 * Enum que representa los diferentes estados de una orden.
 */
public enum EstadoOrden {
    /**
     * Orden creada pero no procesada.
     */
    CREADA,
    /**
     * Orden en proceso de pago.
     */
    EN_PROCESO,
    /**
     * Orden en camino hacia el cliente.
     */
    EN_CAMINO,
    /**
     * Orden entregada al cliente.
     */
    ENTREGADA,
    /**
     * Orden cancelada.
     */
    CANCELADA
}
