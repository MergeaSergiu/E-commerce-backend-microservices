package com.mycompany.app.record;

public record ProductRequest(
        String name,
        Float price,
        Integer quantity,
        String description) { }
