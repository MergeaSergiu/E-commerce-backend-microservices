package com.mycompany.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME_ORDER = "order-exchange";  // Exchange name     // Queue name
    public static final String QUEUE_NAME_ORDER = "order-queue";
    public static final String ROUTING_KEY_ORDER = "order.routing.key"; // Routing key

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME_ORDER, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME_ORDER);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ORDER);
    }
}
