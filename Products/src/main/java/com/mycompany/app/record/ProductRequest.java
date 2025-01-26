package com.mycompany.app.record;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
        @NotBlank
        String name,
        @NotBlank
        Float price,
        @NotBlank
        Integer quantity,
        String description) {


}
