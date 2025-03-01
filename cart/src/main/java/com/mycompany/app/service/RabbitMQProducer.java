package com.mycompany.app.service;

import com.mycompany.app.model.OrderMessageDTO;
import com.mycompany.app.model.PaymentRequest;

public interface RabbitMQProducer {

    void sendMessage(OrderMessageDTO orderMessageDTO);

    void sendPaymentMessage(PaymentRequest paymentRequest);
}
