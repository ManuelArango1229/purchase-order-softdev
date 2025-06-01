package com.softdev.purchase_order.infrastucture.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Clase JwtUtils que proporciona funciones para obtener claims de un token JWT.
 */
public class JwtUtils {

    /**
     * Obtiene el valor de un "claim" (reclamación) específico del token JWT
     * del contexto de seguridad actual.
     * <p>
     * Esta función accede al contexto de seguridad gestionado por Spring Security
     * y, si el usuario autenticado es una instancia de {@link JwtAuthenticationToken},
     * extrae el token JWT y retorna el valor del claim solicitado.
     * </p>
     *
     * @param claim el nombre del claim que se desea obtener desde el JWT.
     * @return el valor del claim como {@link String}, o {@code null} si no hay autenticación
     *         válida o si el claim no está presente.
     */
    public static String getClaim(final String claim) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Jwt jwt = jwtAuthToken.getToken();
            return jwt.getClaimAsString(claim);

        }
        return null;
    }
}
