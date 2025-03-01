package com.mycompany.app.service;

import com.mycompany.app.model.PaymentRequest;
import com.stripe.exception.StripeException;

public interface PaymentService {

    void processPayment(PaymentRequest paymentRequest) throws StripeException;

}
