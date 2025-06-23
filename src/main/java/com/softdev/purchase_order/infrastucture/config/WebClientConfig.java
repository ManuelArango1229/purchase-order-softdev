package com.softdev.purchase_order.infrastucture.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración de WebClient para la aplicación.
 */
@Configuration
public class WebClientConfig {

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
}
