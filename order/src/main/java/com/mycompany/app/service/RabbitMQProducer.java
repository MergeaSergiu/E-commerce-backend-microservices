package com.mycompany.app.service;

import com.mycompany.app.record.OrderMessageDTO;

public interface RabbitMQProducer {

    void sendMessage(OrderMessageDTO orderMessageDTO);
}
