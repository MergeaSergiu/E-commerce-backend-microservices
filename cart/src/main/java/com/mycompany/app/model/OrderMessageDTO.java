package com.mycompany.app.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record OrderMessageDTO(
        String email,
        String message
) implements Serializable {
}

