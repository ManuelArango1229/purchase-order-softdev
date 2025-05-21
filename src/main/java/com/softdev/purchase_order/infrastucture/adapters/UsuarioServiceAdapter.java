package com.softdev.purchase_order.infrastucture.adapters;

import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.softdev.purchase_order.use_cases.dto.response.UsuarioResponse;

/**
 * Adaptador para la interfaz de servicio de usuarios.
 * Implementa la interfaz UsuarioServicePort y utiliza WebClient para realizar llamadas HTTP.
 */
@Component
public class UsuarioServiceAdapter implements UsuarioServicePort {

    /**
     * Cliente WebClient para realizar llamadas HTTP.
     */
    private final WebClient webClient;
    /**
     * URL base del servicio de usuarios.
     */
    private final String usuarioServiceUrl;

    /**
     * Constructor que inicializa el adaptador con el cliente WebClient.
     *
     * @param webClientBuilder Constructor de WebClient.
     */
    public UsuarioServiceAdapter(final WebClient.Builder webClientBuilder) {
        this.usuarioServiceUrl = "http://localhost:8080/usuario"; // Ajusta según la configuración de tu servicio
        this.webClient = webClientBuilder.baseUrl(usuarioServiceUrl).build();
    }

    /**
     * Obtiene la información de un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario.
     * @return Información del usuario.
     */
    @Override
    public UsuarioResponse obtenerUsuario(final String email) {
        return webClient.get()
                .uri("/buscar/{email}", email)
                .retrieve()
                .bodyToMono(UsuarioResponse.class)
                .block();
    }
}
