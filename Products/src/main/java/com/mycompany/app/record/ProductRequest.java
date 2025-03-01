package com.mycompany.app.record;

public record ProductRequest(
        String name,
        Integer price,
        Integer quantity,
        String description) { }
