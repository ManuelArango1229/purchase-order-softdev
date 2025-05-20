package com.softdev.purchase_order.use_cases.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa una solicitud de método de pago.
 */
@AllArgsConstructor
@Getter
public class MetodoPagoRequest {
    /**
     * Tipo de método de pago.
     */
    private String metodo_pago;
    /**
     * Nombre del método de pago.
     */
    private String numeroTarjeta;
    /**
     * Número de tarjeta de crédito o débito.
     */
    private String fechaExpiracion;
    /**
     * Fecha de expiración de la tarjeta.
     */
    private String cvv;
    /**
     * Código de verificación (CVV) de la tarjeta.
     */
    private String nombreTitular;

    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public MetodoPagoRequest() {
    }
}
