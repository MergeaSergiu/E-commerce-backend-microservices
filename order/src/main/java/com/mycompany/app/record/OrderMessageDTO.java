package com.mycompany.app.record;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record OrderMessageDTO(
        String email,
        String message
) implements Serializable {
}
