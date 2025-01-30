package com.mycompany.app.record;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer productId,
        String name,
        Float price,
        Integer quantity,
        String description) { }
