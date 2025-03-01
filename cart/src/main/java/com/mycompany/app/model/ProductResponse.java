package com.mycompany.app.model;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer productId,
        String name,
        Integer price,
        Integer quantity,
        String description) { }
