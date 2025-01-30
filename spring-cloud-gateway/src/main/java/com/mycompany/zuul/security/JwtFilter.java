package com.mycompany.zuul.security;


import com.mycompany.zuul.service.JwtService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class JwtFilter implements GatewayFilter {

    private final RouteValidator validator;
    private final JwtService jwtService;

    public JwtFilter(RouteValidator validator, JwtService jwtService) {
        this.validator = validator;
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (validator.isSecured.test(request)) {
            if(authMissing(exchange.getRequest())) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String authHeader = getAuthHeader(request);
            String token = null;

            if (authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
            if (token == null || jwtService.isTokenExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String username = jwtService.extractClientUsername(token);
            String role = jwtService.extractClientRole(token);
            if(username == null || !role.equals("USER")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }else {
            System.out.println("Endpoint not secured");
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").stream()
                .findFirst()
                .orElse("");
    }
}
