package com.softdev.purchase_order.infrastucture.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; // JSON Converter
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para la aplicación de órdenes de compra.
 * Define el exchange, la cola y el binding, así como el convertidor de mensajes.
 */
@Configuration
public class RabbitMQConfig {
    /**
     * Nombre del exchange utilizado para enviar mensajes de órdenes de compra.
     */
    public static final String EXCHANGE_NAME = "purchaseExchange";
    /**
     * Nombre de la cola donde se recibirán los mensajes de órdenes de compra.
     */
    public static final String QUEUE_NAME = "purchaseQueue";
    /**
     * Clave de enrutamiento utilizada para dirigir los mensajes al exchange.
     */
    public static final String ROUTING_KEY = "purchase.key";

    /**
     * Configuración del exchange, la cola y el binding para RabbitMQ.
     * Utiliza un convertidor de mensajes JSON para serializar y deserializar los mensajes.
     *
     * @return Un objeto DirectExchange configurado con el nombre definido.
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * Configuración de la cola que recibirá los mensajes de órdenes de compra.
     * La cola se vincula al exchange con una clave de enrutamiento específica.
     *
     * @return Una instancia de Queue configurada con el nombre definido.
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    /**
     * Configuración del binding entre la cola y el exchange.
     * Utiliza la clave de enrutamiento para dirigir los mensajes al exchange.
     *
     * @param queue La cola que recibirá los mensajes.
     * @param exchange El exchange al que se vinculará la cola.
     * @return Un objeto Binding que establece la relación entre la cola y el exchange.
     */
    @Bean
    public Binding binding(final Queue queue, final DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .with(ROUTING_KEY);
    }

    /**
     * Configuración del convertidor de mensajes para serializar y deserializar mensajes en formato JSON.
     * Utiliza Jackson2JsonMessageConverter para convertir objetos a JSON y viceversa.
     *
     * @return Un MessageConverter configurado para manejar mensajes en formato JSON.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configuración del RabbitTemplate para enviar mensajes a RabbitMQ.
     * Utiliza el convertidor de mensajes JSON configurado anteriormente.
     *
     * @param connectionFactory La fábrica de conexiones utilizada para crear conexiones a RabbitMQ.
     * @return Un AmqpTemplate configurado para enviar mensajes en formato JSON.
     */
    @Bean
    public AmqpTemplate customRabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
