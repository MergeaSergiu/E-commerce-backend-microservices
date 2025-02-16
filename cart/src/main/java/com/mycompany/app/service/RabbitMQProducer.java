package com.mycompany.app.service;

import com.mycompany.app.model.OrderMessageDTO;

public interface RabbitMQProducer {

    void sendMessage(OrderMessageDTO orderMessageDTO);
}
