package com.mycompany.app.record;

import lombok.Builder;


@Builder
public record ProductResponse(
        Integer productId,
        String name,
        Integer price,
        Integer quantity,
        String imageURL,
        String description) { }
