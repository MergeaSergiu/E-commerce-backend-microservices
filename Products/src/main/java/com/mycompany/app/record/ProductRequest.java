package com.mycompany.app.record;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
        String name,
        Float price,
        Integer quantity,
        String description) {


}
