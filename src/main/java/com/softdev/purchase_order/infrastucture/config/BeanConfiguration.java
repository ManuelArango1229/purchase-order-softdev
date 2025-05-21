package com.softdev.purchase_order.infrastucture.config;

import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.ProductoServicePort;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;
import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;
import com.softdev.purchase_order.use_cases.service.RealizarOrdenService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración de beans para la aplicación.
 * Esta clase define los beans necesarios para la inyección de dependencias.
 */
@Configuration
public class BeanConfiguration {

    /**
     * Crea un bean de WebClient.Builder para realizar llamadas HTTP.
     *
     * @return Un WebClient.Builder configurado.
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    /**
     * Crea un bean de RealizarOrdenPort que implementa la lógica de negocio para realizar órdenes.
     *
     * @param ordenRepository El repositorio de órdenes.
     * @param productoServicePort El servicio de productos.
     * @param usuarioServicePort El servicio de usuarios.
     * @return Un objeto RealizarOrdenPort configurado.
     */
    @Bean
    public RealizarOrdenPort realizarOrdenUseCase(
            final OrdenRepositoryPort ordenRepository,
            final ProductoServicePort productoServicePort,
            final UsuarioServicePort usuarioServicePort) {
        return new RealizarOrdenService(ordenRepository, productoServicePort, usuarioServicePort);
    }
}
