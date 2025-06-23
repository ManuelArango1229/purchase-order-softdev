package com.softdev.purchase_order.infrastucture.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Controlador para pruebas. */
@RestController
public class TestController {

    /** Cliente de descubrimiento de servicios. */
    @Autowired
    private DiscoveryClient discoveryClient;

    /** Obtiene la lista de nombres de servicios registrados.
     *
     * @return La lista de nombres de servicios.
     */
    @GetMapping("/services")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    /** Obtiene la lista de instancias de un servicio.
     *
     * @param serviceName El nombre del servicio.
     * @return La lista de instancias del servicio.
    */
    @GetMapping("/instances/{serviceName}")
    public List<ServiceInstance> getInstances(final @PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }

    /**
     * Muestra un mensaje de prueba.
     *
     * @return El mensaje de prueba.
     */
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
