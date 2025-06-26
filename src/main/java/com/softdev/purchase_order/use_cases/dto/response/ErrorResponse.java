package com.softdev.purchase_order.use_cases.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa una respuesta de error.
 * Contiene un mensaje de error y una descripción del mismo.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    /**
     * Constructor por defecto.
     */
    private String error;
    /**
     * Mensaje de error detallado.
     */
    private String message;
}
