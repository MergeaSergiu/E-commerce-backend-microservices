package com.mycompany.app.model;

import com.mycompany.app.entity.CartStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record CartResponse (
        Integer cartId,
        Integer userId,
        CartStatus status,
        List<ProductResponse> productResponseList
){
}
