package com.mycompany.app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "email-exchange";  // Exchange name
    public static final String QUEUE_NAME = "email-queue";        // Queue name
    public static final String ROUTING_KEY = "email.routing.key";  // Routing key

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);  // Durable queue
    }

    // Bind the queue to the exchange using the routing key
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
