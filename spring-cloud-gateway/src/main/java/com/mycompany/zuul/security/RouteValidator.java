package com.mycompany.zuul.security;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openEndpoints = List.of(
            "/api/v1/auth/**"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
