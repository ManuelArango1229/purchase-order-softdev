package com.softdev.purchase_order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa un método de pago.
 */
@AllArgsConstructor
@Data
public class MetodoPago {
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

    /**
     * Imprime la información del método de pago en formato de cadena.
     *
     * @return Cadena que representa la información del método de pago.
     */
    public String toString() {
        return "MetodoPago{" +
                "nombre='" + nombre + '\'' +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", cvv='" + cvv + '\'' +
                ", nombreTitular='" + nombreTitular + '\'' +
                '}';
    }
}
