package com.mycompany.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routerBuilder(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("customer",r->r.path("/api/v1/customers/**")
                        .uri("lb://customer"))
                .route("fraud",r->r.path("/api/v1/fraud-check/**")
                        .uri("lb://fraud"))
                .route("products", r->r.path("/api/v1/products/**")
                    .uri("lb://products")).build();
    }
}