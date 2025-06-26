package com.softdev.purchase_order.infrastucture.messaging;

import com.softdev.purchase_order.use_cases.dto.response.OrdenConDetallesDTO;

import lombok.Getter;
import lombok.Setter;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Servicio para publicar mensajes de órdenes a RabbitMQ.
 * Utiliza el AmqpTemplate configurado para enviar mensajes en formato JSON.
 */
@Service
@Getter
@Setter
public class OrdenPublisherService {

    /**
     * Template de AMQP para enviar mensajes a RabbitMQ.
     * Se inyecta automáticamente por Spring.
     */
    private final AmqpTemplate amqpTemplate;

    /**
     * Constructor que inyecta el AmqpTemplate configurado.
     *
     * @param amqpTemplateParam El template de AMQP para enviar mensajes.
     */
    public OrdenPublisherService(final @Qualifier("customRabbitTemplate") AmqpTemplate amqpTemplateParam) {
        this.amqpTemplate = amqpTemplateParam;
    }

    /**
     * Publica una orden con sus detalles a la cola de RabbitMQ.
     *
     * @param ordenConDetalles El DTO de la orden a enviar.
     */
    public void publicarOrden(final OrdenConDetallesDTO ordenConDetalles) {
        try {
            amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                ordenConDetalles
            );
            System.out.println("Orden enviada exitosamente: " + ordenConDetalles.getId() + "direccion de entrega: " + ordenConDetalles.getDireccion());
        } catch (Exception e) {
            System.err.println("Error al enviar la orden: " + e.getMessage());
            throw new RuntimeException("Error al publicar la orden en RabbitMQ", e);
        }
    }

    /**
     * Publica una orden con un mensaje personalizado adicional.
     *
     * @param ordenConDetalles El DTO de la orden a enviar.
     * @param mensaje Mensaje adicional para contexto.
     */
    public void publicarOrdenConMensaje(final OrdenConDetallesDTO ordenConDetalles, final String mensaje) {
        try {
            // Crear un wrapper con mensaje adicional si es necesario
            OrdenMensajeWrapper wrapper = new OrdenMensajeWrapper(ordenConDetalles, mensaje);

            amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                wrapper
            );
            System.out.println("Orden con mensaje enviada exitosamente: " + ordenConDetalles.getId());
        } catch (Exception e) {
            System.err.println("Error al enviar la orden con mensaje: " + e.getMessage());
            throw new RuntimeException("Error al publicar la orden en RabbitMQ", e);
        }
    }

    /**
     * Clase wrapper para enviar orden con mensaje adicional.
     */
    public static class OrdenMensajeWrapper {
        /**
         * DTO de la orden con detalles.
         * Incluye información de la orden y un mensaje adicional.
         */
        private OrdenConDetallesDTO orden;
        /**
         * Mensaje adicional para contexto.
         */
        private String mensaje;
        /**
         * Timestamp de cuando se creó el mensaje.
         */
        private String timestamp;

        /**
         * Constructor por defecto necesario para la deserialización de RabbitMQ.
         */
        public OrdenMensajeWrapper() {
        }

        /**
         * Constructor que inicializa el wrapper con la orden y un mensaje.
         *
         * @param ordenParam La orden con detalles a enviar.
         * @param mensajeParam Mensaje adicional para contexto.
         */
        public OrdenMensajeWrapper(final OrdenConDetallesDTO ordenParam, final String mensajeParam) {
            this.orden = ordenParam;
            this.mensaje = mensajeParam;
            this.timestamp = java.time.LocalDateTime.now().toString();
        }
    }
}
