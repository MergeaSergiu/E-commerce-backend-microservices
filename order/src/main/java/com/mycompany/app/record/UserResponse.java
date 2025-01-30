package com.mycompany.app.record;

import lombok.Builder;

@Builder
public record UserResponse(
        Integer userId,
        String username
) {
}
