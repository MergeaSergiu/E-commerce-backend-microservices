package com.mycompany.app.service.impl;

import com.mycompany.app.config.RabbitMQConfig;
import com.mycompany.app.service.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerImpl implements RabbitMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducerImpl.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        logger.info("Sending message to RabbitMQ: {}", message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }
}
