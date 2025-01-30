package com.mycompany.app.record;

import com.mycompany.app.model.PaymentMethod;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        Integer orderId,
        Integer userId,
        Integer productId,
        LocalDateTime orderDate,
        PaymentMethod paymentMethod,
        String username,
        String productName,
        Float productPrice
) {
}
