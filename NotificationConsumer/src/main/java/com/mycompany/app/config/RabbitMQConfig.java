package com.mycompany.app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "email-exchange";  // Exchange name     // Queue name
    public static final String QUEUE_NAME = "email-queue";
    public static final String ROUTING_KEY = "email.routing.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

}
