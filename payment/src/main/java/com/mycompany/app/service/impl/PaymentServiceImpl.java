package com.mycompany.app.service.impl;

import com.mycompany.app.model.PaymentRequest;
import com.mycompany.app.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    public PaymentServiceImpl(
            @Value("${stripe.secret.key}") String stripeSecretKey
    ) {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public void processPayment(PaymentRequest paymentRequest) throws StripeException {
        CustomerCreateParams CustomerParams = CustomerCreateParams.builder()
                .setEmail("testemail@yahoo.com")
                .build();
        String customerId = Customer.create(CustomerParams).getId();
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (paymentRequest.amount() * 100))
                        .setCurrency("USD")
                        .setPaymentMethod("pm_card_visa")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .setCustomer(customerId)
                        .setDescription("Order Purchase")
                        .build();
        PaymentIntent.create(params);
    }
}
