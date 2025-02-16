package com.mycompany.app.model;

public record ProductResponse(
        Integer productId,
        String name,
        Float price,
        Integer quantity,
        String description) { }
