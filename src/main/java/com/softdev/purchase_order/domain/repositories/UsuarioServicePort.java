package com.softdev.purchase_order.domain.repositories;

/**
 * Interfaz que define el caso de uso para obtener un usuario.
 */
public interface UsuarioServicePort {
    /**
     * Verifica si un usuario existe en el sistema.
     *
     * @param email El correo electrónico del usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    UsuarioResponse obtenerUsuario(String email);
}