package com.mycompany.app.model;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer productId,
        String name,
        Float price,
        Integer quantity,
        String description) { }
