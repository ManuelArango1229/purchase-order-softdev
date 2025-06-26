package com.softdev.purchase_order.use_cases.exceptions;

/**
 * Excepción que se lanza cuando una orden de compra es inválida.
 */
public class InvalidPaymentMethodException extends RuntimeException {
    /**
     * Serial version UID for serialization.
     *
     * @param message El mensaje de error que describe la excepción.
     */
    public InvalidPaymentMethodException(final String message) {
        super(message);
    }
}
