package com.softdev.purchase_order.use_cases.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa una solicitud para realizar una orden de compra.
 */
@AllArgsConstructor
@Getter
public class RealizarOrdenRequest {
    /**
     * Nombre del cliente.
     */
    private List<ProductoOrdenRequest> productos;
    /**
     * Método de pago.
     */
    private MetodoPagoRequest metodoPago;

    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public RealizarOrdenRequest() {
    }
}
