package com.mycompany.app.record;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "Email field is empty")
        String email,
        @NotBlank(message = "Password field is empty")
        String password
) {}


