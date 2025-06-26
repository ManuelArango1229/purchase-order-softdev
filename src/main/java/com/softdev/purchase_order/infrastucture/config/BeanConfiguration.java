package com.softdev.purchase_order.infrastucture.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.ProductoServicePort;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;
import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;
import com.softdev.purchase_order.infrastucture.messaging.OrdenPublisherService;
import com.softdev.purchase_order.use_cases.service.RealizarOrdenService;

/**
 * Configuración de beans para la aplicación.
 * Esta clase define los beans necesarios para la inyección de dependencias.
 */
@Configuration
@LoadBalancerClient(name = "product-service")
public class BeanConfiguration {

    /**
     * Crea un bean de WebClient.Builder para realizar llamadas HTTP.
     *
     * @return Un WebClient.Builder configurado.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    /**
     * Crea un bean de WebClient para realizar llamadas HTTP.
     *
     * @param builder El builder de WebClient.
     *
     * @return Un WebClient configurado.
     */
    @Bean
    public WebClient webClient(final @LoadBalanced WebClient.Builder builder) {
        return builder.build();
    }

    /**
     * Crea un bean de RealizarOrdenPort que implementa la lógica de negocio para realizar órdenes.
     *
     * @param ordenRepository El repositorio de órdenes.
     * @param productoServicePort El servicio de productos.
     * @param usuarioServicePort El servicio de usuarios.
     * @param ordenPublisherService El servicio para publicar órdenes en RabbitMQ.
     *
     * @return Un objeto RealizarOrdenPort configurado.
     */
    @Bean
    public RealizarOrdenPort realizarOrdenUseCase(
            final OrdenRepositoryPort ordenRepository,
            final ProductoServicePort productoServicePort,
            final UsuarioServicePort usuarioServicePort,
            final OrdenPublisherService ordenPublisherService) {
        return new RealizarOrdenService(ordenRepository, productoServicePort, usuarioServicePort, ordenPublisherService);
    }
}
