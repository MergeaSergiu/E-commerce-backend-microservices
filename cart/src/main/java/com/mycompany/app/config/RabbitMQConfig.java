package com.mycompany.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME_ORDER = "order-exchange";
    public static final String QUEUE_NAME_ORDER = "order-queue";
    public static final String QUEUE_NAME_PAYMENT = "payment-queue";
    public static final String ROUTING_KEY_ORDER = "order.routing.key";
    public static final String ROUTING_KEY_PAYMENT = "payment.routing.key";

    @Bean
    public Queue orderQueue() {
        return new Queue(QUEUE_NAME_ORDER, true);
    }

    @Bean
    public Queue paymentQueue(){
        return new Queue(QUEUE_NAME_PAYMENT, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME_ORDER);
    }

    @Bean
    public Binding orderbinding(@Qualifier("orderQueue") Queue orderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange).with(ROUTING_KEY_ORDER);
    }

    @Bean
    public Binding paymentBinding(@Qualifier("paymentQueue")Queue paymentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with(ROUTING_KEY_PAYMENT);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
