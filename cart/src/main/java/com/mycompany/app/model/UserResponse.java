package com.mycompany.app.model;

import lombok.Builder;

@Builder
public record UserResponse(
        Integer userId,
        String username
) {
}
