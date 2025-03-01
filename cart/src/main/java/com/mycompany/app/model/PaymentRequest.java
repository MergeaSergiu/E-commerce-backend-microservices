package com.mycompany.app.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PaymentRequest(
        Integer orderId,
        Integer amount
) implements Serializable { }
