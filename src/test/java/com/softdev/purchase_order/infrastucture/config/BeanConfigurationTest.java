package com.softdev.purchase_order.infrastucture.config;

import com.softdev.purchase_order.domain.repositories.OrdenRepositoryPort;
import com.softdev.purchase_order.domain.repositories.ProductoServicePort;
import com.softdev.purchase_order.domain.repositories.RealizarOrdenPort;
import com.softdev.purchase_order.domain.repositories.UsuarioServicePort;
import com.softdev.purchase_order.infrastucture.messaging.OrdenPublisherService;
import com.softdev.purchase_order.use_cases.service.RealizarOrdenService;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeanConfigurationTest {

    private final BeanConfiguration config = new BeanConfiguration();

    @Test
    void testWebClientBuilderNotNull() {
        assertNotNull(config.webClientBuilder());
    }

    @Test
    void testWebClientNotNull() {
        WebClient.Builder builder = config.webClientBuilder();
        WebClient client = config.webClient(builder);
        assertNotNull(client);
    }

    @Test
    void testRealizarOrdenUseCase() {
        OrdenRepositoryPort ordenRepo = mock(OrdenRepositoryPort.class);
        ProductoServicePort productoService = mock(ProductoServicePort.class);
        UsuarioServicePort usuarioService = mock(UsuarioServicePort.class);
        OrdenPublisherService publisherService = mock(OrdenPublisherService.class);

        RealizarOrdenPort useCase = config.realizarOrdenUseCase(
                ordenRepo, productoService, usuarioService, publisherService
        );

        assertNotNull(useCase);
        assertTrue(useCase instanceof RealizarOrdenService);
    }
}
