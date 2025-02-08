package com.mycompany.app.model;

import java.io.Serializable;

public record OrderMessageDTO (
        String email,
        String message
) implements Serializable { }
