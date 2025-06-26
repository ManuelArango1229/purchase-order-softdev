package com.softdev.purchase_order.infrastucture.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un método de pago embebido en una orden de compra.
 */
@Embeddable
@Data
@NoArgsConstructor
public class MetodoPagoEmbeddable {
    /**
     * Nombre del método de pago.
     */
    private String nombre;
    /**
     * Número de tarjeta de crédito o débito.
     */
    private String numeroTarjeta;
    /**
     * Fecha de expiración de la tarjeta.
     */
    private String fechaExpiracion;
    /**
     * Código de verificación (CVV) de la tarjeta.
     */
    private String cvv;
    /**
     * Nombre del titular de la tarjeta.
     */
    private String nombreTitular;
}
