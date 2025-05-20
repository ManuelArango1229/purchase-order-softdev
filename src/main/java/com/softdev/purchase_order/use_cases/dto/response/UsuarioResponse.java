package com.softdev.purchase_order.use_cases.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase que representa la respuesta de un usuario.
 */
@AllArgsConstructor
@Getter
public class UsuarioResponse {
    /**
     * ID del usuario.
     */
    private String email;
    /**
     * Nombre del usuario.
     */
    private String nombre;
    /**
     * Apellido del usuario.
     */
    private String dni;
    
    /**
     * Constructor vacío para la deserialización de JSON.
     */
    public UsuarioResponse() {
    }
}
