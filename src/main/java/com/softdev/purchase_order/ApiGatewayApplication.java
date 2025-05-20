package com.softdev.purchase_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
    /**
     * Método principal que inicia purchase-order.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(final String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
