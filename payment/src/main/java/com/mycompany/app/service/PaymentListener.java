package com.mycompany.app.service;

import com.mycompany.app.config.RabbitMQConfig;
import com.mycompany.app.model.PaymentRequest;
import com.stripe.exception.StripeException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {

    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_PAYMENT)
    public void receivePaymentRequest(PaymentRequest paymentRequest) throws StripeException {
        System.out.println("Received payment request for Order ID: " + paymentRequest.orderId() +", amount: " + paymentRequest.amount());
        paymentService.processPayment(paymentRequest);
    }

}
