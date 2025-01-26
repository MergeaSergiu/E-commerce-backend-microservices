package com.mycompany.app.record;

import lombok.Builder;


@Builder
public record ProductResponse(
        String name,
        Float price,
        Integer quantity,
        String description) { }
