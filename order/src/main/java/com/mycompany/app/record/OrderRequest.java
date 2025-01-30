package com.mycompany.app.record;

import com.mycompany.app.model.PaymentMethod;

public record OrderRequest(

        Integer userId,
        Integer productId,
        PaymentMethod paymentMethod
) {
}
