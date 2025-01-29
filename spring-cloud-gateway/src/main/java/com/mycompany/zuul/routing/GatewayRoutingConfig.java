package com.mycompany.zuul.routing;

import com.mycompany.zuul.security.JwtFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    private final JwtFilter jwtFilter;

    public GatewayRoutingConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("customer",r->r.path("/api/v1/customers/**")
                        .filters(f -> f.filter( jwtFilter))
                        .uri("lb://customer"))
                .route("fraud",r->r.path("/api/v1/fraud-check/**")
                        .filters(f -> f.filter( jwtFilter))
                        .uri("lb://fraud"))
                .route("products", r->r.path("/api/v1/products/**")
                        .filters(f -> f.filter( jwtFilter))
                        .uri("lb://products"))
                .route("authentication", r->r.path("/api/v1/auth/**")
                        .uri("lb://authentication")).build();
    }
}